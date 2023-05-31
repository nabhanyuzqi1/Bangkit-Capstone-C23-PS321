package com.oneplatform.obeng.navigation

import android.transition.Scene
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.ui.screen.*
import com.oneplatform.obeng.ui.screen.components.RegisterForm.RegisterFormTechnician
import com.oneplatform.obeng.ui.screen.components.RegisterForm.RegisterFormUser

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }

        composable(Screen.RegisterUserScreen.route){
            RegisterFormUser(navController = navController)
        }

        composable(Screen.RegisterTechnicianScreen.route){
            RegisterFormTechnician(navController = navController)
        }

        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
    }
}