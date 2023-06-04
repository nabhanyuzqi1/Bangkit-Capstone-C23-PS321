package com.oneplatform.obeng.screen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.navigation.NavScreen
import com.oneplatform.obeng.ui.theme.splash_color
import kotlinx.coroutines.delay
import com.oneplatform.obeng.BuildConfig
import androidx.compose.runtime.*


@Composable
fun SplashScreen(navController: NavController) {
    // Create a mutable state variable to store the debug information
    var debugInformation by remember { mutableStateOf("") }

    // Function to update the debug information
    fun updateDebugInformation(event: String) {
        debugInformation = event
    }
    LaunchedEffect(Unit) {
        // Update the debug information with the event when starting the app (for debug builds only)
        if (BuildConfig.DEBUG) {
            delay(500)
            updateDebugInformation("App started")

            delay(1000)
            updateDebugInformation("Connecting to Server")

            delay(700)
            updateDebugInformation("Connected")

        }
        navController.popBackStack()
        navController.navigate(NavScreen.LoginScreen.route)
        // Invoke the callback to navigate to the login screen after the delay


    }

    // Splash screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = splash_color),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace with your app logo image
            Image(
                painter = painterResource(R.drawable.flower_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Loading circle animation
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(36.dp)
            )

            Text(
                modifier = Modifier.padding(top = 50.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "Capstone Bangkit 2023"
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                text = "Productbase"
            )

            if (BuildConfig.DEBUG) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    text = debugInformation,
                    maxLines = 2
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    //SplashScreen(navController = {}, onTimeout = {})
}
