package com.oneplatform.obeng.ui.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.oneplatform.obeng.R
import com.oneplatform.obeng.ui.screen.components.CustomStyleTextField
import com.oneplatform.obeng.ui.screen.components.SmallBanner
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.gray

@Composable
fun RegisterScreen(){
    var pageCount = remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
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
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Column() {
                                Text(
                                    style = MaterialTheme.typography.titleMedium.copy(color = gray),
                                    fontWeight = FontWeight.Bold,
                                    text = "User Registration")

                                //Username
                                Text(
                                    text = "Username",
                                    style = MaterialTheme.typography.labelSmall.copy(color = gray),
                                    modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
                                )
                                CustomStyleTextField(
                                    "Username",
                                    R.drawable.ic_person,
                                    KeyboardType.Text,
                                    VisualTransformation.None
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
                                    VisualTransformation.None
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
                                    PasswordVisualTransformation()
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
                                    VisualTransformation.None
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
                                    VisualTransformation.None
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
                                    VisualTransformation.None
                                )


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
fun RegisterScreenPreview(){
    RegisterScreen()
}