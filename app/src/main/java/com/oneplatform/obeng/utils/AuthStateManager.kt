import android.content.Context

class AuthStateManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AuthStatePrefs", Context.MODE_PRIVATE)

    fun saveAuthState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    fun getAuthState(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun clearAuthState() {
        sharedPreferences.edit().remove("isLoggedIn").apply()
    }

    fun saveRole(role: String) {
        sharedPreferences.edit().putString("userRole", role).apply()
    }

    fun getRole(): String? {
        return sharedPreferences.getString("userRole", null)
    }
}
