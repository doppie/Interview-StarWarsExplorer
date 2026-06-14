package com.jelletenbrinke.starwarsexplorer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Character Page based on https://swapi.py4e.com/api/people/.
 *
 * Note: Could not find JSON schema, so assumed optionality for all fields.
 */
@Serializable
@JsonIgnoreUnknownKeys
data class CharacterPageData(
    @SerialName("next") val nextPageUrl: String? = null,
    @SerialName("previous") val previousPageUrl: String? = null,
    @SerialName("results") val characters: List<CharacterData> = emptyList(),
)