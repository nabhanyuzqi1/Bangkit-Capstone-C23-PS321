package com.oneplatform.obeng.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItemsUser = listOf(
    BottomNavItem(
        name = "Home",
        route = "home_screen",
        icon = Icons.Rounded.Home
    ),
    BottomNavItem(
        name = "Create",
        route = "order_screen",
        icon = Icons.Rounded.List
    ),
    BottomNavItem(
        name = "Settings",
        route = "notification_screen",
        icon = Icons.Rounded.Notifications
    ),
    BottomNavItem(
        name = "Settings",
        route = "setting_screen",
        icon = Icons.Rounded.Settings
    )
)

val bottomNavItemsTech = listOf(
    BottomNavItem(
        name = "Home",
        route = "techhome_screen",
        icon = Icons.Rounded.Home
    ),
    BottomNavItem(
        name = "Create",
        route = "techorder_screen",
        icon = Icons.Rounded.List
    ),
    BottomNavItem(
        name = "Settings",
        route = "technotification_screen",
        icon = Icons.Rounded.Notifications
    ),
    BottomNavItem(
        name = "Settings",
        route = "techsetting_screen",
        icon = Icons.Rounded.Settings
    )
)
