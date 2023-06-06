package com.oneplatform.obeng

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oneplatform.obeng.navigation.Navigation
import com.oneplatform.obeng.ui.theme.ObengTheme
import com.oneplatform.obeng.utils.RecommendationModel

class MainActivity : ComponentActivity() {
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

    @Composable
    fun ObengComposeUIMain(){
        RecommendationModel()
        val navController = rememberNavController()
        val currentRoute = navController.currentDestination?.route ?: "splash_screen"


        ObengTheme(currentRoute = currentRoute) {
        Surface(color = colorScheme.background) {
            Navigation()
        }
    }
}
