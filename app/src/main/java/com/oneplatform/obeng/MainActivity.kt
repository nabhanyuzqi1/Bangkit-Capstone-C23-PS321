@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oneplatform.obeng.model.DialogViewModel
import com.oneplatform.obeng.navigation.Navigation
import com.oneplatform.obeng.screen.components.dialogs.CustomDialog
import com.oneplatform.obeng.screen.components.dialogs.CustomDialogShow
import com.oneplatform.obeng.ui.theme.ObengTheme
import com.oneplatform.obeng.ui.theme.orange
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
        RecommendationModel()
        val navController = rememberNavController()
        val currentRoute = navController.currentDestination?.route ?: "splash_screen"
        val viewModel: DialogViewModel = viewModel()
        ObengTheme(currentRoute = currentRoute) {
        Surface(color = colorScheme.background) {
            RecommendationModel()
            Navigation()



        //CustomDialogShow(viewModel = viewModel)

        }
    }
}


