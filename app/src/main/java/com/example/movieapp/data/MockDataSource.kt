package com.example.movieapp.data

class MockDataSource {

    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                id = 1,
                title = "Начало",
                originalTitle = "Inception",
                posterUrl = "",
                year = 2010,
                rating = 8.8,
                duration = 148,
                genres = listOf("Фантастика", "Триллер", "Драма"),
                description = "Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим.",
                director = "Кристофер Нолан",
                cast = listOf("Леонардо ДиКаприо", "Джозеф Гордон-Левитт", "Эллиот Пейдж"),
                country = "США",
                ageRating = "16+",
                budget = 160_000_000,
                boxOffice = 829_895_144
            ),
            Movie(
                id = 2,
                title = "Зеленая миля",
                originalTitle = "The Green Mile",
                posterUrl = "",
                year = 1999,
                rating = 9.1,
                duration = 189,
                genres = listOf("Драма", "Криминал", "Фэнтези"),
                description = "Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы 'Холодная гора'. Вновь прибывший обладал поразительным ростом и был пугающе спокоен.",
                director = "Фрэнк Дарабонт",
                cast = listOf("Том Хэнкс", "Майкл Кларк Дункан", "Дэвид Морс"),
                country = "США",
                ageRating = "16+",
                budget = 60_000_000,
                boxOffice = 290_700_000
            ),
            Movie(
                id = 3,
                title = "Темный рыцарь",
                originalTitle = "The Dark Knight",
                posterUrl = "",
                year = 2008,
                rating = 9.0,
                duration = 152,
                genres = listOf("Боевик", "Криминал", "Драма"),
                description = "Бэтмен поднимает ставки в войне с преступностью. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы Готэма от преступности.",
                director = "Кристофер Нолан",
                cast = listOf("Кристиан Бэйл", "Хит Леджер", "Аарон Экхарт"),
                country = "США",
                ageRating = "16+",
                budget = 185_000_000,
                boxOffice = 1_005_000_000
            )
        )
    }
}