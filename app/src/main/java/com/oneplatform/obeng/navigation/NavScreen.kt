package com.oneplatform.obeng.navigation

sealed class NavScreen (val route: String){
    object SplashScreen : NavScreen("splash_screen")
    object LoginScreen : NavScreen("login_screen")
    object RegisterUserScreen : NavScreen("register_user")
    object RegisterTechnicianScreen : NavScreen("register_technician")
    object HomeScreen : NavScreen("home_screen")
    object TechDetail : NavScreen("tech_detail")
    object OrderScreen : NavScreen("order_screen")
    object SettingScreen : NavScreen("setting_screen")

    object NotificationScreen : NavScreen("notification_screen")

    object TechRecommendation : NavScreen("rec_tech")
    object RecommendationResult : NavScreen("rec_result")

}