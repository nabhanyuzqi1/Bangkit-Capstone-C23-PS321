@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.oneplatform.obeng.screen.user

import CustomStyleGroupedCheckbox
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.screen.components.CustomDropdownMenu
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.ui.theme.Red100
import com.oneplatform.obeng.ui.theme.gray

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TechRecommendation(navController: NavController){
    val pageTitle = "Jelaskan Kerusakan"
    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxSize()
                .height(50.dp)) {
                CustomTopBarTitleBack(
                    navController = navController,
                    title = pageTitle,
                    withBack = true
                )
            }
        },
        content = {
            val listState = rememberLazyListState()
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column( modifier = Modifier.fillMaxHeight(1f)) {
                                //Dropdown Jenis Kendaraan
                                Text(
                                    text = "Jenis Kendaraan",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomDropdownMenu(dropDownList = arrayOf("Pilih Kendaraan", "Mobil", "Motor"),
                                    leadingIconId = R.drawable.ic_flat_flower, visualTransformation = VisualTransformation.None
                                ){

                                }

                                //Jenis Kerusakan
                                Text(
                                    text = "Jenis Kerusakan",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomStyleGroupedCheckbox(
                                    mItemsList = listOf(
                                        "Mesin",
                                        "Ban",
                                        "Bodi Kendaraan",
                                        "Interior",
                                        "Oli"
                                    )
                                ){

                                }


                            }
                        }

                    }
                }

                item {
                    //Button Register
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("rec_result") },
                            colors = ButtonDefaults.buttonColors(containerColor = Red100),
                            modifier = Modifier
                                .padding(top = 30.dp, bottom = 34.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                                text = "Show Recommendations",
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TechRecommendationPreview(){
    TechRecommendation(navController = rememberNavController())
}