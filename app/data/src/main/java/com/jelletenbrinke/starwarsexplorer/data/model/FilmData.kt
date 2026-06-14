package com.jelletenbrinke.starwarsexplorer.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Film data model based on JSON schema at https://swapi.py4e.com/api/films/schema.
 *
 * Note: Stripped to the fields needed for the assignment.
 */
@Serializable
@JsonIgnoreUnknownKeys
data class FilmData(
    val url: String,
    val title: String,
)