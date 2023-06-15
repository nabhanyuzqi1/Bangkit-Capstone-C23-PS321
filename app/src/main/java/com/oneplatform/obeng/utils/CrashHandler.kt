
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val stackTrace = getStackTrace(throwable)
        val timestamp = dateFormat.format(Date())

        // Log the exception
        // You can also send the crash report to a server here
        Log.e("CrashHandler", "Uncaught exception occurred on thread: ${thread.name}")
        Log.e("CrashHandler", "Timestamp: $timestamp")
        Log.e("CrashHandler", "Stack trace:\n$stackTrace")

        // Display a toast message to the user
        showToast("An unexpected error occurred. Please try again.")

        // Terminate the current activity
        val mainHandler = android.os.Handler(Looper.getMainLooper())
        mainHandler.post {
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.let { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
            }
        }

        // Terminate the current process after a short delay
        android.os.Handler().postDelayed({
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }, 2000)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun getStackTrace(throwable: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable.printStackTrace(pw)
        return sw.toString()
    }

    companion object {
        fun init(context: Context) {
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            val crashHandler = CrashHandler(context)
            if (defaultHandler != crashHandler) {
                // Set the crash handler as the default handler
                Thread.setDefaultUncaughtExceptionHandler(crashHandler)
            }
        }
    }
}
