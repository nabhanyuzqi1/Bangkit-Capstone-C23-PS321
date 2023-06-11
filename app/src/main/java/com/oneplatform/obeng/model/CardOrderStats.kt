package com.oneplatform.obeng.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.ui.graphics.vector.ImageVector

data class CardOrderStats(
    val name: String,
    val icon: ImageVector,
    val status: String
)

val CardOrderTypes = listOf(
    CardOrderStats(
        name = "Berhasil",
        status = "success",
        icon = Icons.Rounded.Check
    ),
    CardOrderStats(
        name = "Menunggu Persetujuan",
        status = "waiting",
        icon = Icons.Filled.Call
    ),
    CardOrderStats(
        name = "Ditolak Teknisi",
        status = "rejected",
        icon = Icons.Rounded.Close
    ),
    CardOrderStats(
        name = "Dalam Proses Pengerjaan",
        status = "ongoing",
        icon = Icons.Filled.Refresh
    )
)
