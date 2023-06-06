@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.oneplatform.obeng.screen.components.UserhomeNavbar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController){

    Scaffold(topBar = {

    },
        bottomBar = {
            UserhomeNavbar(navController = navController)
        },
        content =  {

        }
    )
}