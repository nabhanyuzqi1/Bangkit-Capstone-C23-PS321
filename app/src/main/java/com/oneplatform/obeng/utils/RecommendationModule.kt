
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import com.google.firebase.ml.custom.FirebaseModelDataType
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions
import com.google.firebase.ml.custom.FirebaseModelInputs
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions

data class PredictionRequest(
    val user_id: Int,
    val needed_ban: Int,
    val needed_mesin: Int,
    val needed_bodi: Int,
    val needed_interior: Int,
    val needed_oli: Int
)

data class PredictionResponse(
    val predictedTechniciansID: Int,
    val predictedRating: Int,
    val recommendedTechniciansID: String
)


@Preview(showBackground = true)
@Composable
fun GetPredictionView() {
    downloadAndMakePrediction(request = PredictionRequest(
        user_id = 1,
        needed_ban = 1,
        needed_bodi = 0,
        needed_interior = 0,
        needed_mesin = 0,
        needed_oli = 0
    ))
}



private fun downloadAndMakePrediction(
    request: PredictionRequest
) {
    try {
        val remoteModel = FirebaseCustomRemoteModel.Builder("model").build()
        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
            .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 7)) // Update the size to 7
            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 5))
            .build()


        FirebaseModelManager.getInstance().download(remoteModel, conditions)
            .addOnCompleteListener {
                // Success.
                val input = Array(1) { FloatArray(6) }
                input[0][0] = request.user_id.toFloat()
                input[0][1] = request.needed_ban.toFloat()
                input[0][2] = request.needed_mesin.toFloat()
                input[0][3] = request.needed_bodi.toFloat()
                input[0][4] = request.needed_interior.toFloat()
                input[0][5] = request.needed_oli.toFloat()

                val inputs = FirebaseModelInputs.Builder()
                    .add(input)
                    .build()

                val options = FirebaseModelInterpreterOptions.Builder(remoteModel).build()
                val interpreter = FirebaseModelInterpreter.getInstance(options)

                if (interpreter != null) {
                    interpreter.run(inputs, inputOutputOptions)
                        .addOnSuccessListener { outputs ->
                            // Retrieve the predicted values and recommended technicians
                            val output = outputs.getOutput<Array<FloatArray>>(0)
                            val predictedTechniciansID = output[0][0].toInt()
                            val predictedRating = output[0][1].toInt()

                            val recommendedTechnicians = ""

                            // Prepare the response payload
                            val response = PredictionResponse(
                                predictedTechniciansID = predictedTechniciansID,
                                predictedRating = predictedRating,
                                recommendedTechniciansID = recommendedTechnicians
                            )

                            Log.d("Response:", response.toString())

                        }
                        .addOnFailureListener { exception ->
                            Log.e("InterpreterError", "Error in interpreter:", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ModelDownloadError", "Error downloading the model:", exception)
            }
    } catch (e: Exception) {
        Log.e("PredictionError", "Error during prediction:", e)
    }
}
