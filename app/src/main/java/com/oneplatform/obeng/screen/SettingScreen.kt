package com.oneplatform.obeng.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oneplatform.obeng.screen.components.CustomUserProfileCard
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.UserHomeNavbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController){
    val pageTitle = "Settings"
    Scaffold(topBar = {
        Box(modifier = Modifier.height(50.dp)) {
            CustomTopBarTitleBack(
                navController = navController,
                title = pageTitle,
                withBack = false
            )
        }
    },
        bottomBar = {
            UserHomeNavbar(navController = navController)
        },
        content =  {
            Box (modifier = Modifier.padding(top = 40.dp).padding(10.dp)){
                CustomUserProfileCard()
                SettingItems()
            }
        }
    )
}