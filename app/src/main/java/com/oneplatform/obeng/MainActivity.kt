package com.oneplatform.obeng

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oneplatform.obeng.navigation.Navigation
import com.oneplatform.obeng.ui.theme.ObengTheme

class MainActivity : ComponentActivity() {
  



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
        
        //getUserData()
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
        val navController = rememberNavController()
        ObengTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
           // FirebaseAuthStatus()
            //LoginTest(navController = navController)
            Navigation()
        }
    }
}
