package com.example.movieapp.domain

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterUrl: String,
    val year: Int,
    val rating: Double,
    val duration: Int,
    val genres: List<String>,
    val description: String,
    val director: String,
    val cast: List<String>,
    val country: String,
    val ageRating: String,
    val budget: Long,
    val boxOffice: Long
)