@file:OptIn(DelicateCoroutinesApi::class)

package com.oneplatform.obeng.screen.user

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.oneplatform.obeng.utils.generateSnapToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



@Preview
@Composable
fun PaymentScreen() {
    val snapToken = remember { mutableStateOf<String?>(null) }
    val redirectUrl = remember { mutableStateOf<String?>(null) }

    Column {
        Button(
            onClick = {
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = generateSnapToken()
                        snapToken.value = response.token
                        redirectUrl.value = response.redirectUrl
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            enabled = snapToken.value == null
        ) {
            Text(text = "Generate Snap Token")
        }

        if (snapToken.value != null && redirectUrl.value != null) {
            Text(text = "Snap Token: ${snapToken.value}", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Redirect URL: ${redirectUrl.value}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
