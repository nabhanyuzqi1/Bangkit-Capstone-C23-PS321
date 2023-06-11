@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.oneplatform.obeng.screen.components.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.primary

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onDismiss: () -> Unit = {},
    dialogHeadline: String = "Default Headline"
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(2.dp, color = White10, shape = RoundedCornerShape(15.dp))
        ) {
            DialogContent(dialogHeadline, onDismiss)
        }
    }
}

@Composable
private fun DialogContent(dialogHeadline: String, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = dialogHeadline,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Nomor Pesanan")
            Text("089123hs")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Waktu Pesanan")
            Text("21:30")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tanggal Pesanan")
            Text("5 Juli 2023")
        }
        Divider()

        Text("Silakan tunggu persetujuan Teknisi", fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = CircleShape
            ) {
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Composable
fun ShowDialog(stateDialog: MutableState<Boolean>){

    if (stateDialog.value) {
        CustomDialog(
            onDismiss = {
                // Handle dialog dismiss logic
                stateDialog.value = false
            },
            dialogHeadline = "Custom Dialog Example"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
    // Other UI content

    // State to track whether the dialog should be shown or not
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CustomDialog(
            onDismiss = {
                // Handle dialog dismiss logic
                showDialog.value = false
            },
            dialogHeadline = "Custom Dialog Example"
        )
    }

    Button(
        onClick = {
            // Set the showDialog state to true to show the dialog
            showDialog.value = true
        }
    ) {
        Text(text = "Open Custom Dialog")
    }
}





