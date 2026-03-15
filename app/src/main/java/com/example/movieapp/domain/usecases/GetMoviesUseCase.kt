package com.example.movieapp.domain.usecases

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MoviesRepository

class GetMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getMovies()
    }
}