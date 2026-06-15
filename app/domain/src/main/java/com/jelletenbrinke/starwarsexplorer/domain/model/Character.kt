package com.jelletenbrinke.starwarsexplorer.domain.model

data class Character(
    val url: String,
    val name: String,
    val height: String,
    val gender: String,
    val films: List<Film>,
    val homeworld: Planet?,
    val birthYear: String,
)