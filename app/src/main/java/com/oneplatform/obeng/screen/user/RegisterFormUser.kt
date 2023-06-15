package com.oneplatform.obeng.screen.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.model.FirebaseAuthModel
import com.oneplatform.obeng.screen.components.CustomStyleTextField
import com.oneplatform.obeng.screen.components.SmallBanner
import com.oneplatform.obeng.ui.theme.Red100
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.gray



@Composable
fun RegisterFormUser(navController: NavController, authRegister: FirebaseAuthModel){
    // Define state variables for form fields
    var usernameState by remember { mutableStateOf(String()) }
    var emailState by remember { mutableStateOf(String()) }
    var passwordState by remember { mutableStateOf(String()) }
    var phoneState by remember { mutableStateOf(String()) }
    var addressState by remember { mutableStateOf(String()) }
    val roleState = "customer"
    val listState = rememberLazyListState()

    fun onRegisterClick() {
        authRegister.registerWithEmailCustomer(
            emailState,
            passwordState,
            usernameState,
            phoneState,
            addressState,
            roleState,
            onSuccess = {
                navController.navigate("login_screen")
            }
        ) {
            // Handle registration failure here
            // Show an error message or perform necessary actions
        }
    }


    LazyColumn(state = listState, modifier = Modifier
        .fillMaxSize()){
        item {
            ConstraintLayout {

                val (logoimageref, loginformref) = createRefs()

                Box(contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .height(100.dp)
                        .constrainAs(logoimageref) {
                            top.linkTo(loginformref.top)
                            bottom.linkTo(loginformref.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    SmallBanner("Register")
                }
                Surface(
                    color = White10,
                    shape = RoundedCornerShape(40.dp).copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(loginformref) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column {
                                Text(
                                    style = MaterialTheme.typography.titleMedium.copy(color = gray),
                                    fontWeight = FontWeight.Bold,
                                    text = "User Registration"
                                )

                                //Username
                                Text(
                                    text = "Username",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 30.dp)
                                )
                                CustomStyleTextField(
                                    "Username",
                                    R.drawable.ic_person,
                                    KeyboardType.Text,
                                    VisualTransformation.None,
                                    onValueChange = { newValue ->
                                        usernameState = newValue
                                    }
                                )

                                //Email
                                Text(
                                    text = "Email",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomStyleTextField(
                                    "Email",
                                    R.drawable.ic_email,
                                    KeyboardType.Email,
                                    VisualTransformation.None,
                                    onValueChange = { newValue ->
                                        emailState = newValue
                                    }
                                )

                                //Password
                                Text(
                                    text = "Password",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomStyleTextField(
                                    "Password",
                                    R.drawable.ic_password,
                                    KeyboardType.Password,
                                    PasswordVisualTransformation(),
                                    onValueChange = { newValue ->
                                        passwordState = newValue
                                    }
                                )

                                //Phone
                                Text(
                                    text = "Phone",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomStyleTextField(
                                    "Phone",
                                    R.drawable.ic_phone,
                                    KeyboardType.Phone,
                                    VisualTransformation.None,
                                    onValueChange = { newValue ->
                                        phoneState = newValue
                                    }
                                )

                                //Address
                                Text(
                                    text = "Address",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
                                )
                                CustomStyleTextField(
                                    "Address",
                                    R.drawable.ic_address,
                                    KeyboardType.Text,
                                    VisualTransformation.None,
                                    onValueChange = { newValue ->
                                        addressState = newValue
                                    }
                                )

                                //Button
                                //Button Register
                                Button(
                                    onClick = {
                                        onRegisterClick()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Red100),
                                    modifier = Modifier
                                        .padding(top = 60.dp, bottom = 34.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                                        text = "Register",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormUserPreview(){
   //RegisterFormUser(navController = rememberNavController())
}
