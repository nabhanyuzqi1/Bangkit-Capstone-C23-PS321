package com.oneplatform.obeng.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = Red20,
    secondary = Red100,
    tertiary = White10
)

private val LightColorScheme = lightColorScheme(
    primary = Red20,
    secondary = Red100,
    tertiary = White10

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
@Composable
fun ObengTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    currentRoute: String, // Add a parameter for the current route
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Set the system bar color based on the current route
    val systemBarColor = when (currentRoute) {
        "home_screen" -> Color.White
        "login_screen" -> primary
        "splash_screen" -> primary
        "register_user" -> primary
        "register_technician" -> primary// Set different colors for different screens/pages
        else -> Color.Transparent // Set a default color if needed
    }

    val routeTextColors = mapOf(
        "home_screen" to true, // Set true or false based on whether the text should be dark
        "login_screen" to false,
        "splash_screen" to false,
        "register_user" to false,
        "register_technician" to false,
        // Add more routes and their associated text colors
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = systemBarColor.toArgb()
            val darkStatusBarText = routeTextColors[currentRoute] ?: true
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkStatusBarText
        }
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

