package com.example.movieapp2.domain.repository

import com.example.movieapp2.data.database.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun addToFavorites(movie: FavoriteMovieEntity)
    suspend fun removeFromFavorites(movieId: String)
    suspend fun getAllFavorites(): List<FavoriteMovieEntity>
    suspend fun isFavorite(movieId: String): Boolean
    fun observeFavorites(): Flow<List<FavoriteMovieEntity>>
}