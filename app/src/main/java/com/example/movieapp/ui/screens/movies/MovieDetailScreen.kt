package com.example.movieapp.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.screens.favorites.favoriteMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController
) {
    // Получаем фильм по ID (пока заглушка)
    val movie = remember(movieId) {
        // Временно создаем фильм для демонстрации
        Movie(
            id = movieId,
            title = "Фильм #$movieId",
            originalTitle = "Movie #$movieId",
            posterUrl = "",
            year = 2024,
            rating = 5.0,
            duration = 120,
            genres = listOf("Демо"),
            description = "Это тестовый фильм из API JSONPlaceholder. Здесь будет описание.",
            director = "JSONPlaceholder",
            cast = listOf("Автор"),
            country = "Online",
            ageRating = "0+",
            budget = 0,
            boxOffice = 0
        )
    }

    var isFavorite by remember { mutableStateOf(false) }

    // Проверяем, есть ли фильм в избранном
    isFavorite = favoriteMovies.any { it.id == movieId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Название: ${movie.title}")
            Text(text = "Год: ${movie.year}")
            Text(text = "Рейтинг: ${movie.rating}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Описание:")
            Text(text = movie.description)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isFavorite) {
                        favoriteMovies.removeAll { it.id == movieId }
                    } else {
                        favoriteMovies.add(movie)
                    }
                    isFavorite = !isFavorite
                }
            ) {
                Text(
                    text = if (isFavorite) "✓ В избранном" else "Добавить в избранное"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text(text = "Назад к списку")
            }
        }
    }
}
