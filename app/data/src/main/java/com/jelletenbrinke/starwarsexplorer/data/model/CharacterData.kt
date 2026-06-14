package com.jelletenbrinke.starwarsexplorer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Character Model based on JSON schema at https://swapi.py4e.com/api/people/schema.
 *
 * Note: Stripped to the fields needed for the assignment.
 */
@Serializable
@JsonIgnoreUnknownKeys
data class CharacterData(
    val url: String,
    val name: String,
    val height: String,
    val gender: String,
    @SerialName("films") val filmUrls: List<String>,
    @SerialName("homeworld") val homeworldUrl: String,
    @SerialName("birth_year") val birthYear: String,
)