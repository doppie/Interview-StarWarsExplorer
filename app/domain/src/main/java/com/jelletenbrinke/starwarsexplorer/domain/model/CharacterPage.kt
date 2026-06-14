package com.jelletenbrinke.starwarsexplorer.domain.model

data class CharacterPage(
    val nextPageUrl: String?,
    val characters: List<Character>
)