package com.clavecillascc.wikinomergeco

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomMenuContent(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)
