@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.model.CardOrderTypes
import com.oneplatform.obeng.screen.components.CardHistoryOrder
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.UserHomeNavbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(navController: NavController){
    val pageTitle = "Order History"
    Scaffold(
        topBar = {
            ConstraintLayout {
                val (topappbarbgref) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(topappbarbgref) {
                            top.linkTo(topappbarbgref.top)
                            bottom.linkTo(topappbarbgref.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    CustomTopBarTitleBack(navController = navController, title = pageTitle, withBack = false)
                }
            }
        },

        bottomBar = {
            UserHomeNavbar(navController = navController)
        },
        content =  {
            Box(modifier = Modifier.padding(top = 56.dp, bottom = 56.dp)) {
                LazyColumn {
                    item {
                        CardHistoryOrder(CardOrderTypes[0])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[1])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[2])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[3])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[3])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[3])
                    }
                    item {
                        CardHistoryOrder(CardOrderTypes[3])
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun OrderScreenPreview(){
    OrderScreen(navController = rememberNavController())
}