@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun TopBarBack(navController: NavController) {
    IconButton(onClick = { navController.navigateUp() }) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "On Back",
            tint = dark_gray,
            modifier = Modifier.size(32.dp, 32.dp)

        )
    }
}
@Composable
fun CustomTopBarTitleBack(navController: NavController, title: String, withBack: Boolean) {
    val useBack = remember{ mutableStateOf(false)}
    Row(
        modifier = Modifier
            .fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (withBack == useBack.value){
            //no back button
        } else{
            TopBarBack(navController = navController)
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ){
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTopBarBackPreview(){
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