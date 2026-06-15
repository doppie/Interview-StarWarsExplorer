package com.jelletenbrinke.starwarsexplorer.characterdetails

import com.jelletenbrinke.starwarsexplorer.domain.model.Character

data class CharacterDetailsUiState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)