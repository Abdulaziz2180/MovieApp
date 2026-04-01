package com.example.movieapp.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MoviesState {
    object Loading : MoviesState()
    data class Success(val movies: List<Movie>) : MoviesState()
    data class Error(val message: String) : MoviesState()
}

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesState>(MoviesState.Loading)
    val uiState: StateFlow<MoviesState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesState.Loading
            try {
                val movies = getMoviesUseCase()
                _uiState.value = MoviesState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MoviesState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}