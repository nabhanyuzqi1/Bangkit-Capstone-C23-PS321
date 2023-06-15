package com.oneplatform.obeng.navigation

sealed class NavScreen (val route: String){
    object SplashScreen : NavScreen("splash_screen")
    object LoginScreen : NavScreen("login_screen")
    object RegisterUserScreen : NavScreen("register_user")
    object RegisterTechnicianScreen : NavScreen("register_technician")
    object HomeScreen : NavScreen("home_screen")
    object TechInformation : NavScreen("tech_information")
    object UserOrderScreen : NavScreen("order_screen")
    object TechOrderScreen : NavScreen("techorder_screen")
    object UserSettingScreen : NavScreen("setting_screen")
    object TechSettingScreen : NavScreen("techsetting_screen")
    object UserNotificationScreen : NavScreen("notification_screen")
    object TechNotificationScreen : NavScreen("technotification_screen")

    object TechRecommendation : NavScreen("rec_tech")
    object RecommendationResult : NavScreen("rec_result")
    object ServiceRequest : NavScreen("service_request")

    object RefundHistory : NavScreen("refund_history")

    object TechHomeScreen : NavScreen("techhome_screen")

}