package com.oneplatform.obeng.screen.components.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.oneplatform.obeng.R
import com.oneplatform.obeng.screen.components.CustomAppSearchBar

@Composable
fun HomeScreenHeader() {
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        ConstraintLayout {
            val (topappbarbgref, popularitemsref) = createRefs()

            Box(modifier = Modifier
                .height(100.dp)
                .constrainAs(topappbarbgref) {
                    top.linkTo(topappbarbgref.top)
                    bottom.linkTo(topappbarbgref.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                HeaderHomePopularItems()
            }

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(40.dp)
                    .copy(bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize), modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp)
                    .constrainAs(popularitemsref) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)) {

                    // here
                }
            }


        }
    }
}



@Composable
fun HeaderHomePopularItems() {
    Image(
        painter = painterResource(id = R.drawable.login_bg),
        contentDescription = "login_bg",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxSize()
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                modifier = Modifier.size(32.dp, 32.dp),
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                tint = Color.White
            )
        }
/*
        Text(
            text = "Dashboard",
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )*/

        CustomAppSearchBar(
            placeHolder = "Cari Teknisi...",
            leadingIconId = R.drawable.ic_search,
            keyboardType = KeyboardType.Text,
            //visualTransformation = VisualTransformation.None
        )

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = Color.White
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenHeaderPreview(){
    HomeScreenHeader()
}