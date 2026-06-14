package com.jelletenbrinke.starwarsexplorer.domain.model

data class CharacterPage(
    val url: String,
    val nextPageUrl: String?,
    val characters: List<Character>
)