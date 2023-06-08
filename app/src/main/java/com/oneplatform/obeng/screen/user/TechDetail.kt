package com.oneplatform.obeng.screen.user

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.model.Technician
import com.oneplatform.obeng.model.techDummyData
import com.oneplatform.obeng.screen.components.CardTechName
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.ui.theme.primary


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechDetail(navController: NavController, cardTechStats: Technician) {
    val pageTitle = "Details"

    Scaffold(
        topBar = {

            Box (modifier = Modifier.height(50.dp)){
                CustomTopBarTitleBack(
                    navController = navController,
                    title = pageTitle,
                    withBack = true
                )
            }
        },

        content = {
            DetailsView(cardTechStats = cardTechStats)
        }
    )
}

@Composable
fun DetailsView(cardTechStats: Technician) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        // Basic details
        item {
            val imageUri = rememberSaveable { mutableStateOf("") }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let { imageUri.value = it.toString() }
            }

            Column(
                modifier = Modifier.run {
                    padding(top = 60.dp)
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(180.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(cardTechStats.techPhoto),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                }
                Text(text = cardTechStats.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = cardTechStats.email, fontSize = 16.sp)
            }
        }

        // My story details
        item {
            CardTechName(cardTechStats = cardTechStats)
        }

        // Quick info
        item {

        }

        // Owner info
        item {

        }

        // CTA - Adopt me button
        item {
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { /* Do something! */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = primary,
                    contentColor = Color.White
                )
            ) {
                Text("Sewa Jasa Sekarang")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TechDetailPreview(){
    TechDetail(navController = rememberNavController(), cardTechStats = techDummyData[0])
}