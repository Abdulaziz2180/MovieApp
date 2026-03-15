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
import com.example.movieapp.data.MockDataSource
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.screens.favorites.favoriteMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController
) {
    val mockDataSource = MockDataSource()
    val movies = mockDataSource.getMovies()

    val movie = movies.find { it.id == movieId }?.let { dataMovie ->
        Movie(
            id = dataMovie.id,
            title = dataMovie.title,
            originalTitle = dataMovie.originalTitle,
            posterUrl = dataMovie.posterUrl,
            year = dataMovie.year,
            rating = dataMovie.rating,
            duration = dataMovie.duration,
            genres = dataMovie.genres,
            description = dataMovie.description,
            director = dataMovie.director,
            cast = dataMovie.cast,
            country = dataMovie.country,
            ageRating = dataMovie.ageRating,
            budget = dataMovie.budget,
            boxOffice = dataMovie.boxOffice
        )
    }

    var isFavorite by remember { mutableStateOf(false) }

    if (movie != null) {
        isFavorite = favoriteMovies.contains(movie)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie?.title ?: "Детали фильма") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (movie != null) {
                Text(text = "Название: ${movie.title}")
                Text(text = "Год: ${movie.year}")
                Text(text = "Рейтинг: ${movie.rating}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isFavorite) {
                            favoriteMovies.remove(movie)
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
            } else {
                Text(text = "Фильм не найден")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Назад")
                }
            }
        }
    }
}
