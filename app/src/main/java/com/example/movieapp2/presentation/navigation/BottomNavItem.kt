package com.example.movieapp2.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Фильмы", Screen.Movies.route, Icons.Default.Movie),
    BottomNavItem("Избранное", Screen.Favorites.route, Icons.Default.Favorite),
    BottomNavItem("Настройки", Screen.Settings.route, Icons.Default.Settings)
)