@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.UserhomeNavbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(navController: NavController){
    val pageTitle = "Notifications"
    Scaffold(topBar = {

        CustomTopBarTitleBack(navController = navController, title = pageTitle, withBack = false)
    },
        bottomBar = {
            UserhomeNavbar(navController = navController)
        },
        content =  {

        }
    )
}