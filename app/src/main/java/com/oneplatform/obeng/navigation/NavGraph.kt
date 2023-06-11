package com.oneplatform.obeng.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.model.techDummyData
import com.oneplatform.obeng.screen.LoginScreen
import com.oneplatform.obeng.screen.NotificationScreen
import com.oneplatform.obeng.screen.OrderScreen
import com.oneplatform.obeng.screen.SettingScreen
import com.oneplatform.obeng.screen.SplashScreen
import com.oneplatform.obeng.screen.technician.RegisterFormTechnician
import com.oneplatform.obeng.screen.user.RecommendationResult
import com.oneplatform.obeng.screen.user.RefundHistory
import com.oneplatform.obeng.screen.user.RegisterFormUser
import com.oneplatform.obeng.screen.user.ServiceRequest
import com.oneplatform.obeng.screen.user.TechInformation
import com.oneplatform.obeng.screen.user.TechRecommendation
import com.oneplatform.obeng.screen.user.UserHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.SplashScreen.route
    ){
        composable(NavScreen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(NavScreen.LoginScreen.route){
            LoginScreen(navController = navController)
        }

        composable(NavScreen.RegisterUserScreen.route){
            RegisterFormUser(navController = navController)
        }

        composable(NavScreen.RegisterTechnicianScreen.route){
            RegisterFormTechnician(navController = navController)
        }

        composable(NavScreen.HomeScreen.route){
            UserHomeScreen(navController = navController)
        }

        composable(NavScreen.TechInformation.route){
            TechInformation(navController = navController, cardTechStats = techDummyData[0])
        }

        composable(NavScreen.OrderScreen.route){
            OrderScreen(navController = navController)
        }

        composable(NavScreen.SettingScreen.route){
            SettingScreen(navController = navController)
        }

        composable(NavScreen.NotificationScreen.route){
            NotificationScreen(navController = navController)
        }
        
        composable(NavScreen.TechRecommendation.route){
            TechRecommendation(navController = navController)
        }
        
        composable(NavScreen.RecommendationResult.route){
            RecommendationResult(navController = navController)
        }

        composable(NavScreen.ServiceRequest.route){
            ServiceRequest(navController = navController)
        }
        
        composable(NavScreen.RefundHistory.route){
            RefundHistory(navController = navController)
        }

    }
}
