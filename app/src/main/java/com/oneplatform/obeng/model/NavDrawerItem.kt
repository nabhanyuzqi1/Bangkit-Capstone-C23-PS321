package com.oneplatform.obeng.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

data class NavDrawerItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val navDrawerItems = listOf(
    NavDrawerItem(Icons.Default.Favorite, "Pengembalian Dana", ""),
    NavDrawerItem(Icons.Default.Face, "Rekomendasi Teknisi", "rec_tech")
)