package com.example.movieapp2.presentation.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp2.data.database.AppDatabase
import com.example.movieapp2.data.repository.FavoritesRepositoryImpl
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repository = remember { FavoritesRepositoryImpl(db) }
    val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.provideFactory(repository))
    val savedMovies by viewModel.savedMovies.collectAsState()
    val scope = rememberCoroutineScope()

    if (savedMovies.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Нет избранных фильмов")
        }
    } else {
        LazyColumn {
            items(savedMovies, key = { it.movieId }) { movie ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = movie.title,
                            modifier = Modifier.size(80.dp, 120.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(movie.title, style = MaterialTheme.typography.titleMedium)
                            movie.year?.let { Text("Год: $it") }
                            movie.rating?.let { Text("Рейтинг: $it") }
                        }
                        Button(onClick = {
                            scope.launch {
                                viewModel.removeFromFavorites(movie.movieId)
                            }
                        }) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    }
}
