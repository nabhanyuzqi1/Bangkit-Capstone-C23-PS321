@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.ui.theme.dark_gray
import com.oneplatform.obeng.ui.theme.gray
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun HomeScreenHeaderPreview(){
}

@Composable
fun HomeScreenHeader(drawerState: DrawerState) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)){
        HeaderUserHome(drawerState = drawerState)
    }
}

@Composable
fun HeaderUserHome(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            scope.launch { drawerState.open() }
        }) {
            Icon(
                modifier = Modifier.size(32.dp, 32.dp),
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                tint = gray
            )
        }
        CustomAppSearchBar(
            placeHolder = "Cari Teknisi...",
            leadingIconId = R.drawable.ic_search,
            keyboardType = KeyboardType.Text,
        )
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = gray
            )
        }
    }
}


@Composable
fun CustomTopBarTitleBack(navController: NavController, title: String, withBack: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (withBack) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "On Back",
                            tint = dark_gray,
                            modifier = Modifier.size(32.dp, 32.dp)

                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.size(46.dp, 46.dp))
            }

        } else {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun CustomTopBarBackPreview(){
    CustomTopBarTitleBack(navController = rememberNavController(), "Preview", withBack = true)
}
@Preview(showBackground = true)
@Composable
fun CustomTopBarBackPreview2(){
    CustomTopBarTitleBack(navController = rememberNavController(), "Preview", withBack = false)
}



@Composable
fun CustomAppSearchBar(
    placeHolder: String,
    leadingIconId: Int,
    keyboardType: KeyboardType,
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent),
        value = textState.value,
        onValueChange = { valueChanged ->
            textState.value = valueChanged
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(text = placeHolder, fontSize = 14.sp)
            } },
        trailingIcon = {
            Image(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(18.dp),
                bitmap = ImageBitmap.imageResource(id = leadingIconId),  // material icon
                colorFilter = ColorFilter.tint(gray),
                contentDescription = "custom_text_field"
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = gray,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = gray,
            disabledTrailingIconColor = gray,
        ),
        shape = RoundedCornerShape(10.dp), // Adjust the corner radius as needed
        textStyle = TextStyle(color = gray, fontSize = 16.sp, textAlign = TextAlign.Center)
    )
}





@Preview(showBackground = false)
@Composable
fun CustomAppSearchBarPreview(){
    CustomAppSearchBar("Cari teknisi..", R.drawable.ic_search, KeyboardType.Text )
}