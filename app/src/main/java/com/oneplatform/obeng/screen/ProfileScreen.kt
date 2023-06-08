@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.screen.components.CustomProfileCard
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.UserHomeNavbar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController){
    val pageTitle = "User Profile"
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
                CustomProfileCard()
                SettingItems()
            }
        }
    )
}

@Composable
fun SettingItems() {
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(navController = rememberNavController())
}