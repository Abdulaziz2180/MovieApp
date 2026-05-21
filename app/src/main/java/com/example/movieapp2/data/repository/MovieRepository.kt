package com.example.movieapp2.data.repository

import com.example.movieapp2.data.model.MovieItem
import com.example.movieapp2.data.model.Poster
import com.example.movieapp2.data.model.Vote
import com.example.movieapp2.data.model.CrewMember
import com.example.movieapp2.data.model.ProductionCountry

class MovieRepository {

    fun getAllMovies(): List<MovieItem> = allMovies

    fun getMovieById(uid: String): MovieItem? = allMovies.find { it.id == uid }

    companion object {
        val allMovies = listOf(
            // Интерстеллар
            MovieItem(
                id = "tt0816692",
                type = "movie",
                title = "Интерстеллар",
                originalTitle = "Interstellar",
                poster = Poster(
                    url = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jp"
                ),
                year = 2014,
                runtimeSec = 10140,
                genres = listOf("Фантастика", "Драма", "Приключения"),
                rating = Vote(averageScore = 8.6, totalVotes = 2200000),
                plot = "Группа исследователей отправляется через червоточину в поисках нового дома для человечества.",
                directors = listOf(
                    CrewMember(id = "nm0004716", fullName = "Кристофер Нолан")
                ),
                stars = listOf(
                    CrewMember(id = "nm0000190", fullName = "Мэттью МакКонахи"),
                    CrewMember(id = "nm0000993", fullName = "Энн Хэтэуэй"),
                    CrewMember(id = "nm0000354", fullName = "Джессика Честейн")
                ),
                countries = listOf(ProductionCountry(code = "US", name = "США"), ProductionCountry(code = "GB", name = "Великобритания"))
            ),
            // Леон
            MovieItem(
                id = "tt0110413",
                type = "movie",
                title = "Леон",
                originalTitle = "Léon",
                poster = Poster(
                    url = "https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/f78067ac-10d3-4290-a967-0194eeaa5f59/3840x"
                ),
                year = 1994,
                runtimeSec = 6600,
                genres = listOf("Боевик", "Драма", "Криминал"),
                rating = Vote(averageScore = 8.5, totalVotes = 1300000),
                plot = "Профессиональный убийца берёт под опеку 12-летнюю девочку, чья семья была убита коррумпированным полицейским.",
                directors = listOf(
                    CrewMember(id = "nm0001425", fullName = "Люк Бессон")
                ),
                stars = listOf(
                    CrewMember(id = "nm0000249", fullName = "Жан Рено"),
                    CrewMember(id = "nm0000204", fullName = "Натали Портман"),
                    CrewMember(id = "nm0000168", fullName = "Гэри Олдмен")
                ),
                countries = listOf(ProductionCountry(code = "FR", name = "Франция"))
            ),
            // Побег из Шоушенка
            MovieItem(
                id = "tt0111161",
                type = "movie",
                title = "Побег из Шоушенка",
                originalTitle = "The Shawshank Redemption",
                poster = Poster(
                    url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/eae33fc1-bcb5-450e-89bf-9ba077b24cdf/3840x"
                ),
                year = 1994,
                runtimeSec = 8520,
                genres = listOf("Драма"),
                rating = Vote(averageScore = 9.3, totalVotes = 3000000),
                plot = "Банкир, осуждённый за убийство жены, находит надежду и дружбу в тюрьме Шоушенк.",
                directors = listOf(
                    CrewMember(id = "nm0001104", fullName = "Фрэнк Дарабонт")
                ),
                stars = listOf(
                    CrewMember(id = "nm0000209", fullName = "Тим Роббинс"),
                    CrewMember(id = "nm0000151", fullName = "Морган Фримен")
                ),
                countries = listOf(ProductionCountry(code = "US", name = "США"))
            )
        )
    }
}