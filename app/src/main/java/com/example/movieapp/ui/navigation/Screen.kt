package com.example.movieapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Movies : Screen("movies")
    object MovieDetail : Screen("movies/{movieId}") {
        fun passId(movieId: Int): String = "movies/$movieId"
    }
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
}