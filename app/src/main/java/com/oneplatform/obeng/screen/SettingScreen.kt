@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import AuthStateManager
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.model.FirebaseAuthModel
import com.oneplatform.obeng.screen.components.ButtonSetting
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.CustomUserProfileCard
import com.oneplatform.obeng.screen.components.TechHomeNavbar
import com.oneplatform.obeng.screen.components.UserHomeNavbar
import com.oneplatform.obeng.utils.FirebaseAuthImpl

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun UserSettingScreen(navController: NavController, authStateManager: AuthStateManager){
    val pageTitle = "Settings"
    Scaffold(topBar = {
        Box(modifier = Modifier.height(50.dp)) {
            CustomTopBarTitleBack(
                navController = navController,
                title = pageTitle,
                withBack = false
            )
        }
    },
        bottomBar = {
            UserHomeNavbar(navController = navController)
        },
        content =  {
            Column (modifier = Modifier.fillMaxSize()){

                    Box (modifier = Modifier
                        .padding(top = 40.dp)
                        .padding(10.dp)){
                        CustomUserProfileCard()

                    }


                    SettingItems(authStateManager = authStateManager, navController = navController)

            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TechSettingScreen(navController: NavController, authStateManager: AuthStateManager){
    val pageTitle = "Settings"
    Scaffold(topBar = {
        Box(modifier = Modifier.height(50.dp)) {
            CustomTopBarTitleBack(
                navController = navController,
                title = pageTitle,
                withBack = false
            )
        }
    },
        bottomBar = {
            TechHomeNavbar(navController = navController)
        },
        content =  {
            Column (modifier = Modifier.fillMaxSize()){

                Box (modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(10.dp)){
                    CustomUserProfileCard()

                }


                SettingItems(authStateManager = authStateManager, navController = navController)

            }
        }
    )
}


@Composable
fun SettingItems(authStateManager: AuthStateManager, navController: NavController) {
    ButtonSetting(buttonText = "Logout") {
        val firebaseAuthModel: FirebaseAuthModel = FirebaseAuthImpl(authStateManager = authStateManager)

        firebaseAuthModel.logout(
            onSuccess = {
                // Handle successful logout

                authStateManager.saveAuthState(false)
                navController.navigate("login_screen")
            },
            onFailed = { exception ->
                // Handle logout failure or any exceptions
                exception?.printStackTrace()
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UserSettingScreePreview(){
    UserSettingScreen(navController = rememberNavController(), authStateManager = AuthStateManager(context = LocalContext.current))
}
@Preview(showBackground = true)
@Composable
fun TechSettingScreePreview(){
    TechSettingScreen(navController = rememberNavController(), authStateManager = AuthStateManager(context = LocalContext.current))
}