package com.example.movieapp.data

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MoviesRepository

class MoviesRepositoryImpl(
    private val networkDataSource: NetworkMovieDataSource
) : MoviesRepository {

    override suspend fun getMovies(): List<Movie> {
        return networkDataSource.getPopularMovies()
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return getMovies().find { it.id == id }
    }
}