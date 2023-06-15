@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng

import AuthStateManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.navigation.Navigation
import com.oneplatform.obeng.ui.theme.ObengTheme

class MainActivity : ComponentActivity() {

    private lateinit var authStateManager: AuthStateManager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CrashHandler.init(this)
        // Initialize the AuthStateManager
        authStateManager = AuthStateManager(applicationContext)

        // Check the authentication state
        val isLoggedIn = authStateManager.getAuthState()
        Log.d("isLoggedIn? ", isLoggedIn.toString())
        if (isLoggedIn) {
            setContent {
                NavigateIsLoggedIn(isLoggedIn = isLoggedIn, context = applicationContext)
            }
        }else{
            setContent {
                ObengComposeUIMain(isLoggedIn = isLoggedIn, context = applicationContext)
            }
        }
    }
}
@Composable
private fun NavigateIsLoggedIn(isLoggedIn: Boolean, context: Context){
    Surface(color = colorScheme.background) {
        Navigation(isLoggedIn = isLoggedIn, context = context)
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ObengComposeUIMain(isLoggedIn:Boolean, context: Context){
        val navController = rememberNavController()
        val currentRoute = navController.currentDestination?.route ?: "splash_screen"

        ObengTheme(currentRoute = currentRoute) {
        Surface(color = colorScheme.background) {
            Navigation(isLoggedIn = isLoggedIn, context = context)
        }
    }
}


