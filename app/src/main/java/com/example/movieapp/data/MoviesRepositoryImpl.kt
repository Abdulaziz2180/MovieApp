package com.example.movieapp.data

import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MoviesRepository

class MoviesRepositoryImpl(
    private val dataSource: MockDataSource
) : MoviesRepository {

    override suspend fun getMovies(): List<Movie> {
        return dataSource.getMovies().map { movieData ->
            Movie(
                id = movieData.id,
                title = movieData.title,
                originalTitle = movieData.originalTitle,
                posterUrl = movieData.posterUrl,
                year = movieData.year,
                rating = movieData.rating,
                duration = movieData.duration,
                genres = movieData.genres,
                description = movieData.description,
                director = movieData.director,
                cast = movieData.cast,
                country = movieData.country,
                ageRating = movieData.ageRating,
                budget = movieData.budget,
                boxOffice = movieData.boxOffice
            )
        }
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return getMovies().find { it.id == id }
    }
}