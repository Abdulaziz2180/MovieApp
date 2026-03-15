package com.example.movieapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Главная", Screen.Home.route, Icons.Default.Home),
    BottomNavItem("Фильмы", Screen.Movies.route, Icons.Default.Movie),
    BottomNavItem("Избранное", Screen.Favorites.route, Icons.Default.Favorite),
    BottomNavItem("Профиль", Screen.Profile.route, Icons.Default.Person)
)