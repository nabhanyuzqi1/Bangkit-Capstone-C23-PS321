package com.oneplatform.obeng.navigation

import AuthStateManager
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oneplatform.obeng.api.TechnicianApiService
import com.oneplatform.obeng.api.apiService
import com.oneplatform.obeng.model.TechnicianViewModel
import com.oneplatform.obeng.screen.LoginScreen
import com.oneplatform.obeng.screen.SplashScreen
import com.oneplatform.obeng.screen.TechNotificationScreen
import com.oneplatform.obeng.screen.TechOrderScreen
import com.oneplatform.obeng.screen.TechSettingScreen
import com.oneplatform.obeng.screen.UserNotificationScreen
import com.oneplatform.obeng.screen.UserOrderScreen
import com.oneplatform.obeng.screen.UserSettingScreen
import com.oneplatform.obeng.screen.technician.RegisterFormTechnician
import com.oneplatform.obeng.screen.technician.TechHomeScreen
import com.oneplatform.obeng.screen.user.RecommendationResult
import com.oneplatform.obeng.screen.user.RefundHistory
import com.oneplatform.obeng.screen.user.RegisterFormUser
import com.oneplatform.obeng.screen.user.ServiceRequest
import com.oneplatform.obeng.screen.user.TechInformation
import com.oneplatform.obeng.screen.user.TechRecommendation
import com.oneplatform.obeng.screen.user.UserHomeScreen
import com.oneplatform.obeng.utils.FirebaseAuthImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(isLoggedIn: Boolean, context: Context){
    val navController = rememberNavController()
    val authStateManager = AuthStateManager(context)
    val technicianViewModel = remember { TechnicianViewModel(apiService) }
    val authRegister = FirebaseAuthImpl(authStateManager)
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) getStartDestination(authStateManager) else NavScreen.SplashScreen.route
    ){
        composable(NavScreen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(NavScreen.LoginScreen.route){
            LoginScreen(navController = navController, context = context)
        }

        composable(NavScreen.RegisterUserScreen.route){
            RegisterFormUser(navController = navController, authRegister = authRegister)
        }

        composable(NavScreen.RegisterTechnicianScreen.route){
            RegisterFormTechnician(navController = navController, authRegister = authRegister)
        }

        composable(NavScreen.HomeScreen.route){
            UserHomeScreen(navController = navController, authStateManager = authStateManager, context = context, apiService = TechnicianApiService.create())
        }

        composable(
            route = "tech_information/{technicianId}",
            arguments = listOf(navArgument("technicianId") { type = NavType.StringType })
        ) { backStackEntry ->
            val technicianId = backStackEntry.arguments?.getString("technicianId")
            TechInformation(navController = navController, technicianId = technicianId, technicianViewModel = TechnicianViewModel(apiService = TechnicianApiService.create()))
        }

        composable(NavScreen.UserOrderScreen.route){
            UserOrderScreen(navController = navController)
        }

        composable(NavScreen.TechOrderScreen.route){
            TechOrderScreen(navController = navController)
        }

        composable(NavScreen.UserSettingScreen.route){
            UserSettingScreen(navController = navController, authStateManager = authStateManager)
        }

        composable(NavScreen.TechSettingScreen.route){
            TechSettingScreen(navController = navController, authStateManager = authStateManager)
        }

        composable(NavScreen.UserNotificationScreen.route){
            UserNotificationScreen(navController = navController)
        }

        composable(NavScreen.TechNotificationScreen.route){
            TechNotificationScreen(navController = navController)
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

        composable(NavScreen.TechHomeScreen.route){
            TechHomeScreen(navController = navController, authStateManager = authStateManager, context = context)
        }

    }
}

@Composable
fun getStartDestination(authStateManager: AuthStateManager): String {
    val role = authStateManager.getRole() // Retrieve the user's role from the AuthStateManager
    return when (role) {
        "customer" -> NavScreen.HomeScreen.route
        "technician" -> NavScreen.TechHomeScreen.route
        else -> NavScreen.SplashScreen.route
    }
}
