
package com.oneplatform.obeng.utils

import com.oneplatform.obeng.model.SnapResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun generateSnapToken(): SnapResponse = suspendCoroutine { continuation ->
    val client = OkHttpClient()

    val mediaType = "application/json".toMediaTypeOrNull()
    val body = RequestBody.create(
        mediaType,
        "{\"transaction_details\":{\"order_id\":\"order-id\",\"gross_amount\":10000},\"credit_card\":{\"secure\":true}}"
    )
    val request = Request.Builder()
        .url("https://app.sandbox.midtrans.com/snap/v1/transactions")
        .post(body)
        .addHeader("accept", "application/json")
        .addHeader("content-type", "application/json")
        .addHeader(
            "authorization",
            "Basic U0ItTWlkLXNlcnZlci1INEdiZ051amZZcDEyeW1TdXYtcVdmeUk6"
        )
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val jsonResponse = response.body?.string()
            val jsonObject = JSONObject(jsonResponse.toString())
            val token = jsonObject.optString("token")
            val redirectUrl = jsonObject.optString("redirect_url")
            val snapResponse = SnapResponse(token, redirectUrl)
            continuation.resume(snapResponse)
        }

        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }
    })
}