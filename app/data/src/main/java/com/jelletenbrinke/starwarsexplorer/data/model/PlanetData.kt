package com.jelletenbrinke.starwarsexplorer.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

/**
 * Planet data model based on JSON schema at https://swapi.py4e.com/api/planets/schema.
 *
 * Note: Stripped to the fields needed for the assignment.
 */
@Serializable
@JsonIgnoreUnknownKeys
data class PlanetData(
    val url: String,
    val name: String
)