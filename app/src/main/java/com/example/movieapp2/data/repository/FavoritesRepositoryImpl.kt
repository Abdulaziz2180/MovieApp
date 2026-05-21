package com.example.movieapp2.data.repository

import com.example.movieapp2.data.database.AppDatabase
import com.example.movieapp2.data.database.FavoriteMovieEntity
import com.example.movieapp2.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(private val db: AppDatabase) : FavoritesRepository {

    override suspend fun addToFavorites(movie: FavoriteMovieEntity) {
        db.favoriteMovieDao().addToFavorites(movie)
    }

    override suspend fun removeFromFavorites(movieId: String) {
        db.favoriteMovieDao().removeFromFavorites(movieId)
    }

    override suspend fun getAllFavorites(): List<FavoriteMovieEntity> {
        return db.favoriteMovieDao().getAllFavorites()
    }

    override suspend fun isFavorite(movieId: String): Boolean {
        return db.favoriteMovieDao().isFavorite(movieId)
    }

    override fun observeFavorites(): Flow<List<FavoriteMovieEntity>> {
        return db.favoriteMovieDao().observeFavorites()
    }
}