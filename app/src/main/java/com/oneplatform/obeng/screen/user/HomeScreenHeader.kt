@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.oneplatform.obeng.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oneplatform.obeng.R
import com.oneplatform.obeng.screen.components.CustomAppSearchBar
import com.oneplatform.obeng.ui.theme.gray
import kotlinx.coroutines.launch

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



@Preview(showBackground = true)
@Composable
fun HomeScreenHeaderPreview(){
}
