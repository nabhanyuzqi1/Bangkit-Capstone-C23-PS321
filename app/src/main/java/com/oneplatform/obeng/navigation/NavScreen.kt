package com.oneplatform.obeng.navigation

sealed class NavScreen (val route: String){
    object SplashScreen : NavScreen("splash_screen")
    object LoginScreen : NavScreen("login_screen")
    object RegisterUserScreen : NavScreen("register_user")
    object RegisterTechnicianScreen : NavScreen("register_technician")
    object HomeScreen : NavScreen("home_screen")

}