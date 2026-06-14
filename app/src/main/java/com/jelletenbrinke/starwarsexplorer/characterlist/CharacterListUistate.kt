package com.jelletenbrinke.starwarsexplorer.characterlist

import com.jelletenbrinke.starwarsexplorer.domain.model.Character
import com.jelletenbrinke.starwarsexplorer.domain.model.CharacterPage

data class CharacterListUiState(
    val pages: List<CharacterPage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val nextPageUrl: String? = null,
) {
    val characters: List<Character> = pages.flatMap { it.characters }
    val canLoadMore: Boolean = nextPageUrl != null && !isLoading
}
