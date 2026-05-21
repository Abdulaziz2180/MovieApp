package com.example.movieapp2.presentation.navigation

sealed class Screen(val route: String) {
    object Movies : Screen("movies")
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
    object MovieDetails : Screen("movie/{movieId}") {
        fun createRoute(movieId: String) = "movie/$movieId"
    }
}