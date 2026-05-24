package com.example.movieapp2.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.movieapp2.data.datastore.FilterSettingsStore
import com.example.movieapp2.presentation.details.MovieDetailsView
import com.example.movieapp2.presentation.favorites.FavoritesScreen
import com.example.movieapp2.presentation.films.MovieListScreen
import com.example.movieapp2.presentation.settings.FilterScreen
import com.example.movieapp2.presentation.settings.FilterViewModel
import com.example.movieapp2.presentation.settings.FilterViewModelFactory
import com.example.movieapp2.presentation.profile.ProfileScreen
import com.example.movieapp2.presentation.profile.EditProfileScreen

@Composable
fun BottomNavBar(navController: NavHostController) {
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
                        popUpTo(Screen.Movies.route) { saveState = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Movies.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Movies.route) {
                MovieListScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    },
                    onSettingsClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(route = Screen.Favorites.route) {
                FavoritesScreen()
            }

            composable(route = Screen.Settings.route) {
                val context = LocalContext.current
                val dataStore = remember { FilterSettingsStore(context) }
                val viewModel: FilterViewModel = viewModel(factory = FilterViewModelFactory(dataStore))
                FilterScreen(
                    onBackClick = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }

            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.StringType }
                ),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                MovieDetailsView(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(route = Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }

            composable(route = Screen.EditProfile.route) {
                EditProfileScreen(navController = navController)
            }
        }
    }
}