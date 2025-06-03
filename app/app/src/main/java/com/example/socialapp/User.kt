package com.example.socialapp

data class User(
    val id: Int,
    val name: String,
    val bio: String,
    val isFriend: Boolean,
    val posts: List<String> = listOf()
)

val sampleUsers = listOf(
    User(1, "Катя", "Люблю подорожі та каву", false, listOf("Побувала в Парижі!", "Обожнюю каву вранці")),
    User(2, "Олексій", "Геймер та айтішник", false, listOf("Нова гра Elden Ring — топ!", "Почав вивчати Kotlin ")),
    User(3, "Марина", "Обожнюю книги та море ", false, listOf("Завершила книгу про психологію", "Мрію про відпустку на Кіпрі")),
    User(4, "Антон", "Біг і музика — моє все", false, listOf("Пробіг 10 км сьогодні", "Новий плейлист на Spotify просто вогонь ")),
    User(5, "Ірина", "Фанатка серіалів та смачної їжі", false, listOf("Переглянула весь сезон Stranger Things!", "Сьогодні пекла брауні"))
)
