package com.example.movieapp2.presentation.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp2.data.datastore.FilterParams
import com.example.movieapp2.data.datastore.FilterSettingsStore
import com.example.movieapp2.data.model.MovieItem
import com.example.movieapp2.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

sealed class MovieListUiState {
    object Loading : MovieListUiState()
    data class Success(val movies: List<MovieItem>) : MovieListUiState()
    data class Error(val message: String) : MovieListUiState()
}

class MovieListViewModel(
    private val repository: MovieRepository = MovieRepository(),
    private val settingsStore: FilterSettingsStore? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    private var allMovies: List<MovieItem> = emptyList()
    private var currentFilters: FilterParams = FilterParams("Все", 0)

    init {
        loadMovies()
        observeFilters()
    }

    private fun observeFilters() {
        settingsStore?.let { store ->
            viewModelScope.launch {
                store.settingsFlow
                    .onEach { filters ->
                        currentFilters = filters
                        applyFilters()
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    private fun applyFilters() {
        if (allMovies.isEmpty()) return

        val filtered = allMovies.filter { movie ->
            val categoryMatch = currentFilters.category == "Все" ||
                    movie.genres?.contains(currentFilters.category) == true
            val scoreMatch = (movie.rating?.averageScore ?: 0.0) >= currentFilters.minScore
            categoryMatch && scoreMatch
        }
        _uiState.value = MovieListUiState.Success(filtered)

        val hasActiveFilters = currentFilters.category != "Все" || currentFilters.minScore != 0
        _isFilterActive.value = hasActiveFilters
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            try {
                allMovies = repository.getAllMovies()
                applyFilters()
            } catch (e: Exception) {
                _uiState.value = MovieListUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun sortMoviesByYear() {
        val sorted = allMovies.sortedByDescending { it.year }
        allMovies = sorted
        applyFilters()
    }
}