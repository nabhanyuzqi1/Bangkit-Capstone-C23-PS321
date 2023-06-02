@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneplatform.obeng.R
import com.oneplatform.obeng.ui.theme.secondary

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
            .background(Color.Transparent),
        value = textState.value,
        onValueChange = { valueChanged ->
            textState.value = valueChanged
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { Text(text = placeHolder) },
        trailingIcon = {
            Image(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(18.dp),
                bitmap = ImageBitmap.imageResource(id = leadingIconId),  // material icon
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "custom_text_field"
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            placeholderColor = Color.White,
            focusedBorderColor = secondary,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.White,
            disabledTrailingIconColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp), // Adjust the corner radius as needed
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
    )
}




@Preview(showBackground = false)
@Composable
fun CustomAppSearchBarPreview(){
    CustomAppSearchBar("Cari teknisi..", R.drawable.ic_search, KeyboardType.Text )
}