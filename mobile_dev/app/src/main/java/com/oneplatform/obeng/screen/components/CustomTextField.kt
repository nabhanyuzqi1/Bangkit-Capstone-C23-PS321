@file:OptIn(ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneplatform.obeng.R
import com.oneplatform.obeng.ui.theme.Red20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomStyleTextField(
    placeHolder: String,
    leadingIconId: Int,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    maxLines: Int = 1
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        value = textState.value,

        maxLines = maxLines,
        onValueChange = { valueChanged ->
            textState.value = valueChanged
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { Text(text = placeHolder) },
        leadingIcon = {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Image(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .size(18.dp),
                        bitmap = ImageBitmap.imageResource(id = leadingIconId),  // material icon
                        colorFilter = ColorFilter.tint(Red20),
                        contentDescription = "custom_text_field"
                    )
                    Canvas(
                        modifier = Modifier.height(24.dp)
                    ) {
                        // Allows you to draw a line between two points (p1 & p2) on the canvas.
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2.0F
                        )
                    }
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Red20,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.White,
            disabledTrailingIconColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp), // Adjust the corner radius as needed
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        visualTransformation = visualTransformation
    )
}


@Preview(showBackground = false)
@Composable
fun CustomTextFieldPreview(){
    CustomStyleTextField(
        "Password",
        R.drawable.ic_password,
        KeyboardType.Password,
        PasswordVisualTransformation()
    )
}