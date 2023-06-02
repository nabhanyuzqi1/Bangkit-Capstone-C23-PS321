@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.utils

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


fun CobaLogin(email: String, password: String, navController: NavController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Log.d("Apakah Login Berhasil", "YA")
                Log.d("String", task.toString())
                Log.d("Email", email.toString())
                Log.d("Password", password.toString())

                // Navigate to another screen (HomeScreen) on successful login
                navController.navigate("home_screen")
            } else {
                // Login failed
                val exception = task.exception
                // Handle the exception or log the error
                exception?.printStackTrace()
            }
        }
}




@Preview
@Composable
fun PreviewLoginScreen() {
   //LoginTest(navController = rememberNavController())
}
