package com.example.movieapp2.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movieapp2.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieRepo = MovieRepository()

    private val _selectedMovie = MutableStateFlow<com.example.movieapp2.data.model.MovieItem?>(null)
    val selectedMovie: StateFlow<com.example.movieapp2.data.model.MovieItem?> = _selectedMovie.asStateFlow()

    init {
        val movieUid = savedStateHandle.get<String>("movieId")
        movieUid?.let { loadMovieById(it) }
    }

    private fun loadMovieById(uid: String) {
        _selectedMovie.value = movieRepo.getMovieById(uid)
    }
}