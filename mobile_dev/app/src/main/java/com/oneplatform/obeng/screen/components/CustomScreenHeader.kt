package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneplatform.obeng.R

@Composable
fun HeaderView() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        bitmap = ImageBitmap.imageResource(id = R.drawable.login_bg),
        contentScale = ContentScale.FillWidth,
        contentDescription = "header_view_login_bg"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Image(
            modifier = Modifier.wrapContentWidth().size(60.dp),
            bitmap = ImageBitmap.imageResource(id = R.drawable.flower_logo),
            contentDescription = "header_view_flower_logo",
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = "Obeng",
            color = Color.White,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.josefin_sans_semibold_italic)),
                letterSpacing = 2.sp
            )
        )
    }
}


@Preview(showBackground = false)
@Composable
fun HeaderViewPreview(){
    HeaderView()
}