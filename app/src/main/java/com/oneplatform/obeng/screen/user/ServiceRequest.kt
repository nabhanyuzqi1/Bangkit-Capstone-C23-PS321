package com.oneplatform.obeng.screen.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.oneplatform.obeng.R
import com.oneplatform.obeng.model.DialogViewModel
import com.oneplatform.obeng.model.techDummyData
import com.oneplatform.obeng.screen.components.CardPayment
import com.oneplatform.obeng.screen.components.CustomDropdownMenu
import com.oneplatform.obeng.screen.components.CustomStyleTextField
import com.oneplatform.obeng.screen.components.CustomTechProfileCard
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.ImagePicker
import com.oneplatform.obeng.screen.components.dialogs.CustomDialog
import com.oneplatform.obeng.ui.theme.Red100

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ServiceRequest(navController: NavController) {
    val pageTitle = "Service Request"
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .background(Color.White)
            ) {
                CustomTopBarTitleBack(
                    navController = navController,
                    title = pageTitle,
                    withBack = true
                )
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .padding(25.dp)
            ) {
                item {
                    CustomTechProfileCard()
                }
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Lokasi Anda Beranda",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                        GMapsView()
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Jenis Kendaraan",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                        CustomDropdownMenu(
                            leadingIconId = R.drawable.ic_flat_flower,
                            dropDownList = arrayOf("Mobil", "Motor"),
                            visualTransformation = VisualTransformation.None
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Foto Keadaan Barang",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                        ImagePicker()
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Detail Kerusakan",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                        CustomStyleTextField(
                            placeHolder = "Detail Kerusakan",
                            leadingIconId = R.drawable.ic_technician,
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None,
                            maxLines = 4
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Rincian Pembayaran",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))

                        CardPayment(cardTechStats = techDummyData[0])
                        ButtonPayment(DialogModel = DialogViewModel())
                    }

                }

            }

        }
    )
}

@Composable
fun ButtonPayment(DialogModel: DialogViewModel) {

    if (DialogModel.isDialogShown) {
        CustomDialog(
            onDismiss = {
                DialogModel.onDismissDialog()
            },
            dialogHeadline = "Custom Dialog Example"
        )
    }
    Button(
        onClick = {
            //navController.popBackStack()
            //navController.navigate("home_screen")

            DialogModel.onClicked()

        },
        colors = ButtonDefaults.buttonColors(containerColor = Red100),
        modifier = Modifier
            .padding(top = 30.dp, bottom = 34.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "Bayar",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Composable
fun GMapsView(){
    
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Box(modifier = Modifier
        .height(250.dp)
        .fillMaxWidth()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun ServiceRequestPreview(){
    ServiceRequest(navController = rememberNavController())
}
