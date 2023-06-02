package com.oneplatform.obeng.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.screen.HomeScreen
import com.oneplatform.obeng.screen.LoginScreen
import com.oneplatform.obeng.screen.SplashScreen
import com.oneplatform.obeng.screen.components.RegisterForm.RegisterFormTechnician
import com.oneplatform.obeng.screen.components.RegisterForm.RegisterFormUser

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