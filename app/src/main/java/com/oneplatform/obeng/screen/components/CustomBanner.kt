package com.oneplatform.obeng.screen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneplatform.obeng.R

@Composable
fun SmallBanner(pageTitle: String) {
    Image(
        painter = painterResource(id = R.drawable.login_bg),
        contentDescription = "login_bg",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
    ) {
        Image(

            bitmap = ImageBitmap.imageResource(id = R.drawable.flower_logo),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = pageTitle,
            color = Color.White,
            fontSize = 22.sp,
            letterSpacing = 2.sp,

            modifier = Modifier.padding(5.dp)
        )
    }
}


@Preview(showBackground = false)
@Composable
fun SmallBannerPreview(){
   SmallBanner(pageTitle = "Obeng")
}