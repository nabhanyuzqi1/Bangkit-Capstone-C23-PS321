package com.oneplatform.obeng.utils

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import com.google.firebase.ml.custom.FirebaseModelDataType
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions
import com.google.firebase.ml.custom.FirebaseModelInputs
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun RecommendationModel(){
    ModelStream()

}

fun ModelStream() {
    val remoteModel = FirebaseCustomRemoteModel.Builder("recmm").build()
    val conditions = FirebaseModelDownloadConditions.Builder().build()
    val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
        .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 6))
        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1,5)) // Modify the output shape if needed
        .build()

    FirebaseModelManager.getInstance().download(remoteModel, conditions)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("isModelDownloaded?: ", "Yes: ${conditions.toString()}")
                // Model download successful
                // You can now use the model for inference

                // Create an input array
                val user_id = 8
                val needed_mesin = 1
                val needed_ban = 0
                val needed_bodi = 0
                val needed_interior = 0
                val needed_oli = 0

                val inputArray = floatArrayOf(
                    user_id.toFloat(), needed_mesin.toFloat(), needed_ban.toFloat(),
                    needed_bodi.toFloat(), needed_interior.toFloat(), needed_oli.toFloat()
                )
                val buffer = ByteBuffer.allocateDirect(inputArray.size * 4) // 4 bytes for each float value
                    .order(ByteOrder.nativeOrder())
                for (value in inputArray) {
                    buffer.putFloat(value)
                }
                buffer.rewind()

                val inputs = FirebaseModelInputs.Builder()
                    .add(buffer)
                    .build()

                val options = FirebaseModelInterpreterOptions.Builder(remoteModel).build()
                val interpreter = FirebaseModelInterpreter.getInstance(options)

                interpreter?.run(inputs, inputOutputOptions)
                    ?.addOnSuccessListener { result ->
                        // Process the inference result
                        val output = result.getOutput<Array<FloatArray>>(0)
                        // Handle the output values
                        Log.d("Output Model: ", output.contentDeepToString())
                    }
                    ?.addOnFailureListener { e ->
                        // Handle the inference failure
                        Log.e("Inference Failure", "Failed to run the interpreter", e)
                    }
            } else {
                // Model download failed
                val exception = task.exception
                Log.d("Model Exception", exception.toString())
            }
        }
}
