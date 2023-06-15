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
import com.oneplatform.obeng.model.bottomNavItemsTech
import com.oneplatform.obeng.model.bottomNavItemsUser
import com.oneplatform.obeng.navigation.BottomNavManager


@Composable
fun UserHomeNavbar(navController: NavController) {
    val selectedIndex = BottomNavManager.selectedIndex.value

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        bottomNavItemsUser.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (index != selectedIndex) {
                        BottomNavManager.selectedIndex.value = index
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

@Composable
fun TechHomeNavbar(navController: NavController) {
    val selectedIndex = BottomNavManager.selectedIndex.value

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        bottomNavItemsTech.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (index != selectedIndex) {
                        BottomNavManager.selectedIndex.value = index
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
