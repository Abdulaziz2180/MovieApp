package com.example.movieapp2.presentation.films

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp2.data.database.AppDatabase
import com.example.movieapp2.data.database.FavoriteMovieEntity
import com.example.movieapp2.data.repository.FavoritesRepositoryImpl
import com.example.movieapp2.data.model.MovieItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    listViewModel: MovieListViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiStateValue by listViewModel.uiState.collectAsState()
    val isFilterActive by listViewModel.isFilterActive.collectAsState()
    val scope = rememberCoroutineScope()

    val db = remember { AppDatabase.getInstance(context) }
    val favRepository = remember { FavoritesRepositoryImpl(db) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои фильмы", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                ),
                actions = {
                    IconButton(onClick = { listViewModel.sortMoviesByYear() }) {
                        Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Сортировка", tint = Color.White)
                    }
                    IconButton(onClick = onSettingsClick) {
                        BadgedBox(badge = { if (isFilterActive) Badge() }) {
                            Icon(Icons.Default.Settings, contentDescription = "Фильтры", tint = Color.White)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiStateValue) {
            is MovieListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF6200EE))
                }
            }
            is MovieListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            onFavoriteClick = {
                                Toast.makeText(context, "Сохранено: ${movie.title}", Toast.LENGTH_SHORT).show()
                                scope.launch {
                                    val favorite = FavoriteMovieEntity(
                                        movieId = movie.id,
                                        title = movie.title,
                                        posterUrl = movie.poster?.url,
                                        year = movie.year,
                                        rating = movie.rating?.averageScore,
                                        genres = movie.genres?.joinToString(",")
                                    )
                                    favRepository.addToFavorites(favorite)
                                }
                            }
                        )
                    }
                }
            }
            is MovieListUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: ${state.message}", color = Color.Red)
                }
            }
        }
    }
}

@Composable
private fun MovieCard(
    movie: MovieItem,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = movie.poster?.url,
                contentDescription = "Постер ${movie.title}",
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(6.dp))


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "★ ${movie.averageScore}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFF4B400)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = movie.durationText,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF666666)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))


                val yearAndGenres = buildString {
                    movie.year?.let { append(it) }
                    if (movie.genres?.isNotEmpty() == true) {
                        if (isNotEmpty()) append(" • ")
                        append(movie.genreList)
                    }
                }

                Text(
                    text = yearAndGenres.ifEmpty { "Нет данных" },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF888888)
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "В избранное",
                    tint = Color(0xFFE91E63)
                )
            }
        }
    }
}