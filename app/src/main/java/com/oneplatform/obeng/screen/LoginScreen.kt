@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.screen.components.CustomStyleTextField
import com.oneplatform.obeng.screen.components.HeaderView
import com.oneplatform.obeng.ui.theme.ObengTheme
import com.oneplatform.obeng.ui.theme.Red100
import com.oneplatform.obeng.ui.theme.Red20
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.dark_gray
import com.oneplatform.obeng.ui.theme.gray
import com.oneplatform.obeng.ui.theme.light_gray
import com.oneplatform.obeng.utils.CobaLogin

@Composable
fun LoginScreen(navController: NavController) {
    ObengTheme() {
        val loginPages = listOf("Customer", "Technician")
        val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTabIndex.value) {
                    0 -> CustomerLoginPage(navController = navController)
                    1 -> TechnicianLoginPage(navController = navController)
                }
            }

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                loginPages.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CustomerLoginPage(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(White10),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ConstraintLayout (modifier = Modifier.fillMaxHeight()){
                val (image, loginForm) = createRefs()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(image) {
                            top.linkTo(loginForm.top)
                            bottom.linkTo(loginForm.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    LoginScreenHeader()
                }
                Card(
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = White10
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp)
                        .constrainAs(loginForm) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {
                        val loginText = "Log in to your account."
                        val loginWord = "Log in"
                        val loginAnnotatedString = buildAnnotatedString {
                            append(loginText)
                            addStyle(
                                style = SpanStyle(
                                    color = dark_gray,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
                                ),
                                start = 0,
                                end = loginText.length
                            )
                            addStyle(
                                style = SpanStyle(
                                    color = White10,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                                ),
                                start = 0,
                                end = loginWord.length
                            )
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp),

                            style = MaterialTheme.typography.headlineMedium.copy(color = gray),
                            text = "Create your Customer Account!",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Email Address",
                            style = MaterialTheme.typography.labelSmall.copy(color = gray),
                            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                        )

                        CustomStyleTextField(
                            "Email Address",
                            R.drawable.ic_email,
                            KeyboardType.Email,
                            VisualTransformation.None
                        )

                        Text(
                            text = "Password",
                            style = MaterialTheme.typography.labelSmall.copy(color = gray),
                            modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
                        )
                        CustomStyleTextField(
                            "Password",
                            R.drawable.ic_password,
                            KeyboardType.Password,
                            PasswordVisualTransformation()
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = "Forgot Password?",
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelMedium.copy(color = Red20)
                        )
                        Button(
                            onClick = {
                                //navController.popBackStack()
                                //navController.navigate("home_screen")
                                CobaLogin(email = "nabhan@example.com", password = "coba123", navController)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Red100),
                            modifier = Modifier
                                .padding(top = 30.dp, bottom = 34.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                                text = "Login",
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        val signUpText = "Don't have an account? Sign Up"
                        val signUpWord = "Sign Up"
                        val signUpAnnotatedString = buildAnnotatedString {
                            append(signUpText)
                            addStyle(
                                style = SpanStyle(
                                    color = light_gray,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
                                ),
                                start = 0,
                                end = signUpText.length
                            )
                            addStyle(
                                style = SpanStyle(
                                    color = Red20,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                                ),
                                start = signUpText.indexOf(signUpWord),
                                end = signUpText.length
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            ClickableText(
                                text = signUpAnnotatedString,
                                style = TextStyle(fontSize = 14.sp),
                                onClick = { offset ->
                                    if (offset in signUpText.indexOf(signUpWord) until signUpText.length) {
                                        // Handle click event here
                                        // For example, navigate to the sign-up screen
                                        navController.navigate("register_user")
                                    }
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TechnicianLoginPage(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ConstraintLayout {
                val (image, loginForm) = createRefs()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(image) {
                            top.linkTo(loginForm.top)
                            bottom.linkTo(loginForm.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    LoginScreenHeader()
                }
                Card(
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = White10
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp)
                        .constrainAs(loginForm) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {
                        val loginText = "Log in to your account."
                        val loginWord = "Log in"
                        val loginAnnotatedString = buildAnnotatedString {
                            append(loginText)
                            addStyle(
                                style = SpanStyle(
                                    color = dark_gray,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
                                ),
                                start = 0,
                                end = loginText.length
                            )
                            addStyle(
                                style = SpanStyle(
                                    color = White10,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                                ),
                                start = 0,
                                end = loginWord.length
                            )
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp),

                            style = MaterialTheme.typography.headlineMedium.copy(color = gray),
                            text = "Create your Technician Account!",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Email Address",
                            style = MaterialTheme.typography.labelSmall.copy(color = gray),
                            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                        )

                        CustomStyleTextField(
                            "Email Address",
                            R.drawable.ic_email,
                            KeyboardType.Email,
                            VisualTransformation.None
                        )

                        Text(
                            text = "Password",
                            style = MaterialTheme.typography.labelSmall.copy(color = gray),
                            modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
                        )
                        CustomStyleTextField(
                            "Password",
                            R.drawable.ic_password,
                            KeyboardType.Password,
                            PasswordVisualTransformation()
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = "Forgot Password?",
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelMedium.copy(color = Red20)
                        )
                        Button(
                            onClick = {
                                //navController.popBackStack()
                                //navController.navigate("home_screen")
                                      },
                            colors = ButtonDefaults.buttonColors(containerColor = Red100),
                            modifier = Modifier
                                .padding(top = 30.dp, bottom = 34.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                                text = "Login",
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        val signUpText = "Don't have an account? Sign Up"
                        val signUpWord = "Sign Up"
                        val signUpAnnotatedString = buildAnnotatedString {
                            append(signUpText)
                            addStyle(
                                style = SpanStyle(
                                    color = light_gray,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_regular))
                                ),
                                start = 0,
                                end = signUpText.length
                            )
                            addStyle(
                                style = SpanStyle(
                                    color = Red20,
                                    fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                                ),
                                start = signUpText.indexOf(signUpWord),
                                end = signUpText.length
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            ClickableText(
                                text = signUpAnnotatedString,
                                style = TextStyle(fontSize = 14.sp),
                                onClick = { offset ->
                                    if (offset in signUpText.indexOf(signUpWord) until signUpText.length) {
                                        // Handle click event here
                                        // For example, navigate to the sign-up screen
                                        navController.navigate("register_technician")
                                    }
                                }
                            )
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun LoginScreenHeader() {
    HeaderView()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginScreen(navController = rememberNavController())
}

