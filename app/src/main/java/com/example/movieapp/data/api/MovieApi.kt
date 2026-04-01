package com.example.movieapp.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int
    ): PostDto
}