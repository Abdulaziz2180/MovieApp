package com.example.movieapp2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    val id: String,
    val type: String,
    @SerialName("primaryTitle")
    val title: String,
    @SerialName("originalTitle")
    val originalTitle: String? = null,
    @SerialName("primaryImage")
    val poster: Poster? = null,
    @SerialName("startYear")
    val year: Int? = null,
    @SerialName("runtimeSeconds")
    val runtimeSec: Int? = null,
    val genres: List<String>? = null,
    val rating: Vote? = null,
    val plot: String? = null,
    val directors: List<CrewMember>? = null,
    val writers: List<CrewMember>? = null,
    val stars: List<CrewMember>? = null,
    @SerialName("originCountries")
    val countries: List<ProductionCountry>? = null,
    @SerialName("spokenLanguages")
    val languages: List<SpokenLang>? = null
) {
    val durationText: String
        get() = runtimeSec?.let { seconds ->
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            if (hours > 0) "${hours}ч ${minutes}мин" else "${minutes} мин"
        } ?: "N/A"

    val genreList: String
        get() = genres?.joinToString(", ") ?: "N/A"

    val averageScore: String
        get() = rating?.averageScore?.toString() ?: "N/A"

    val castText: String
        get() = stars?.take(3)?.joinToString(", ") { it.fullName } ?: "N/A"

    val directorText: String
        get() = directors?.joinToString(", ") { it.fullName } ?: "N/A"

    val countryText: String
        get() = countries?.joinToString(", ") { it.name } ?: "N/A"
}

@Serializable
data class Poster(
    val url: String,
    val width: Int? = null,
    val height: Int? = null
)

@Serializable
data class Vote(
    @SerialName("aggregateRating")
    val averageScore: Double,
    @SerialName("voteCount")
    val totalVotes: Int
)

@Serializable
data class CrewMember(
    val id: String,
    @SerialName("displayName")
    val fullName: String,
    @SerialName("primaryImage")
    val photo: Poster? = null,
    @SerialName("primaryProfessions")
    val jobs: List<String>? = null
)

@Serializable
data class ProductionCountry(
    val code: String,
    val name: String
)

@Serializable
data class SpokenLang(
    val code: String,
    val name: String
)

@Serializable
data class MovieListResponse(
    val titles: List<MovieItem>
)