package com.example.movieapp.data

import com.example.movieapp.data.api.RetrofitClient
import com.example.movieapp.domain.Movie

class NetworkMovieDataSource {

    private val api = RetrofitClient.movieApi

    suspend fun getPopularMovies(): List<Movie> {
        return try {
            val posts = api.getPosts()
            posts.map { post ->
                Movie(
                    id = post.id,
                    title = post.title,
                    originalTitle = post.title,
                    posterUrl = "",
                    year = 2024,
                    rating = 5.0,
                    duration = 120,
                    genres = listOf("Данные", "API"),
                    description = post.body,
                    director = "JSONPlaceholder",
                    cast = listOf("Автор: ${post.userId}"),
                    country = "Online",
                    ageRating = "0+",
                    budget = 0,
                    boxOffice = 0
                )
            }
        } catch (e: Exception) {
            throw Exception("Ошибка загрузки данных: ${e.message}")
        }
    }
}