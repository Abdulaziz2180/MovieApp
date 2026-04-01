package com.example.movieapp.di

import com.example.movieapp.data.MoviesRepositoryImpl
import com.example.movieapp.data.NetworkMovieDataSource
import com.example.movieapp.domain.MoviesRepository
import com.example.movieapp.domain.usecases.GetMovieByIdUseCase
import com.example.movieapp.domain.usecases.GetMoviesUseCase
import com.example.movieapp.ui.screens.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NetworkMovieDataSource() }
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
    factory { GetMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }
    viewModel { MoviesViewModel(get()) }
}