package com.example.movieapp.domain.usecases

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MoviesRepository

class GetMovieByIdUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Movie? {
        return repository.getMovieById(id)
    }
}