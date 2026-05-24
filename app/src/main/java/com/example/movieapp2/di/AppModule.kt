package com.example.movieapp2.di

import com.example.movieapp2.data.datastore.FilterSettingsStore
import com.example.movieapp2.data.datastore.ProfileDataStore
import com.example.movieapp2.data.repository.MovieRepository
import com.example.movieapp2.data.database.AppDatabase
import com.example.movieapp2.data.repository.FavoritesRepositoryImpl
import com.example.movieapp2.domain.repository.FavoritesRepository
import com.example.movieapp2.presentation.films.MovieListViewModel
import com.example.movieapp2.presentation.favorites.FavoritesViewModel
import com.example.movieapp2.presentation.settings.FilterViewModel
import com.example.movieapp2.presentation.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // DataStore
    single { FilterSettingsStore(get()) }
    single { ProfileDataStore(get()) }

    // Room
    single { AppDatabase.getInstance(get()) }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }

    // Repository
    single { MovieRepository() }

    // ViewModels
    viewModel { MovieListViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}