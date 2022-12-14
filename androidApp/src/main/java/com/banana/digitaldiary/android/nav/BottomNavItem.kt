package com.banana.digitaldiary.android.nav

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val name: String,
    val route: String,
    val icon: ImageVector,
    val hasBadge: Boolean = false
)