package com.example.movieapp2.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp2.data.database.FavoriteMovieEntity
import com.example.movieapp2.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _savedMovies = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val savedMovies: StateFlow<List<FavoriteMovieEntity>> = _savedMovies.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.observeFavorites()
                .catch { e -> e.printStackTrace() }
                .collect { list ->
                    _savedMovies.value = list
                }
        }
    }

    fun removeFromFavorites(movieId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(movieId)
        }
    }

    companion object {
        fun provideFactory(repository: FavoritesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(repository) as T
                }
            }
    }
}