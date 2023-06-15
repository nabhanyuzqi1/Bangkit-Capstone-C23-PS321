package com.oneplatform.obeng.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.oneplatform.obeng.model.BottomNavItem
import com.oneplatform.obeng.model.bottomNavItemsUser

object BottomNavManager {
    val selectedIndex = mutableStateOf(0)
    private val visitedItems = mutableStateListOf<BottomNavItem>()
    private val visitedStatus = mutableStateMapOf<String, Boolean>().apply {
        // Initialize all items as not visited initially
        bottomNavItemsUser.forEach { item ->
            this[item.route] = false
        }
    }

    fun isVisited(item: BottomNavItem): Boolean {
        return visitedStatus[item.route] ?: false
    }

    fun updateVisitedStatus(item: BottomNavItem) {
        if (!isVisited(item)) {
            visitedItems.add(item)
            visitedStatus[item.route] = true
        }
    }
}
