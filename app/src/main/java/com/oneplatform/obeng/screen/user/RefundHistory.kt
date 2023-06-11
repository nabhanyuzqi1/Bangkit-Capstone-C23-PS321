@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.oneplatform.obeng.screen.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.model.CardOrderStats
import com.oneplatform.obeng.screen.components.CardHistoryOrder
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.ui.theme.ObengTheme

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RefundHistory(navController: NavController){

    val currentRoute = navController.currentDestination?.route ?: ""

    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    val pageTitle = "Refund"
    Scaffold(
        topBar = {

            ObengTheme(currentRoute = currentRoute) {
                val loginPages = listOf("Refundable", "Requested")
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.background(Color.White)) {
                        CustomTopBarTitleBack(
                            navController = navController,
                            title = pageTitle,
                            withBack = true,
                        )
                    }


                    TabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ) {
                        loginPages.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex.value == index,
                                onClick = { selectedTabIndex.value = index },
                                modifier = Modifier
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }

                }
            }
        },
        bottomBar = {},
        content = {
            Box(modifier = Modifier.padding(top = 100.dp)) {
                when (selectedTabIndex.value) {
                    0 -> RefundableOrder()
                    1 -> RefundableRequest()
                }
            }
        }
    )

}

@Composable
fun RefundableOrder(){
    CardHistoryOrder(cardOrderStats = CardOrderStats("Ditolak Teknisi", Icons.Default.Clear, "Gagal"))
}
@Composable
fun RefundableRequest(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RefundHistoryPreview(){
    RefundHistory(navController = rememberNavController())
}