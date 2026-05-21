package com.example.movieapp2.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val movieId: String,
    val title: String,
    val posterUrl: String?,
    val year: Int?,
    val rating: Double?,
    val genres: String?
)