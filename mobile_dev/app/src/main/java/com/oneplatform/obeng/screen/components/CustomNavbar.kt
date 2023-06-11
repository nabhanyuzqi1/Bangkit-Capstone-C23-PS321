package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oneplatform.obeng.model.BottomNavigationManager
import com.oneplatform.obeng.model.bottomNavItemsUser

@Composable
fun UserHomeNavbar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        bottomNavItemsUser.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == BottomNavigationManager.selectedIndex.value,
                onClick = {
                    if (index != BottomNavigationManager.selectedIndex.value) {
                        BottomNavigationManager.selectedIndex.value = index
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon"
                    )
                }
            )
        }
    }
}
