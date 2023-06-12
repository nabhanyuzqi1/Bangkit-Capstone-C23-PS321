@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oneplatform.obeng.ui.theme.ObengTheme
import com.oneplatform.obeng.utils.RecommendationModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObengComposeUIMain()
        }
    }
}


@Composable
fun FirebaseAuthStatus() {
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    Text(
        text = if (isLoggedIn) "User is logged in" else "User is not logged in",
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ObengComposeUIMain(){
        val navController = rememberNavController()
        val currentRoute = navController.currentDestination?.route ?: "splash_screen"
        ObengTheme(currentRoute = currentRoute) {
        Surface(color = colorScheme.background) {
            RecommendationModel()
            //Navigation()
            //PaymentScreen()
        }
    }
}


