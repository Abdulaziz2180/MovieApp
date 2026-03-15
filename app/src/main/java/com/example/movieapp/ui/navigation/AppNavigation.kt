package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.screens.favorites.FavoritesScreen
import com.example.movieapp.ui.screens.home.HomeScreen
import com.example.movieapp.ui.screens.movies.MovieDetailScreen
import com.example.movieapp.ui.screens.movies.MoviesScreen
import com.example.movieapp.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            homeScreen(navController)
            moviesScreen(navController)
            movieDetailScreen(navController)
            favoritesScreen()
            profileScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
}

fun NavGraphBuilder.moviesScreen(navController: NavController) {
    composable(Screen.Movies.route) {
        MoviesScreen(navController)
    }
}

fun NavGraphBuilder.movieDetailScreen(navController: NavController) {
    composable(
        route = Screen.MovieDetail.route,
        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
        MovieDetailScreen(movieId = movieId, navController = navController)
    }
}

fun NavGraphBuilder.favoritesScreen() {
    composable(Screen.Favorites.route) {
        FavoritesScreen()
    }
}

fun NavGraphBuilder.profileScreen() {
    composable(Screen.Profile.route) {
        ProfileScreen()
    }
}