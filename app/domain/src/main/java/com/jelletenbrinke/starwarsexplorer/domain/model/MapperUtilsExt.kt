package com.jelletenbrinke.starwarsexplorer.domain.model

import com.jelletenbrinke.starwarsexplorer.data.model.CharacterData
import com.jelletenbrinke.starwarsexplorer.data.model.CharacterPageData
import com.jelletenbrinke.starwarsexplorer.data.model.FilmData
import com.jelletenbrinke.starwarsexplorer.data.model.PlanetData

/**
 * DataModel to DomainModel Mapper utility extension functions.
 */

internal fun CharacterPageData.toCharacterPage(
    url: String,
    films: Map<String, Film> = emptyMap(),
    planets: Map<String, Planet> = emptyMap()
) = CharacterPage(
    url = url,
    nextPageUrl = nextPageUrl,
    characters = characters.map { it.toCharacter(films, planets) },
)

internal fun CharacterData.toCharacter(
    films: Map<String, Film> = emptyMap(),
    planets: Map<String, Planet> = emptyMap()
) = Character(
        url = url,
        name = name,
        height = height,
        birthYear = birthYear,
        gender = gender,
        homeworld = planets[homeworldUrl],
        films = filmUrls.mapNotNull { films[it] }
    )

internal fun FilmData.toFilm() = Film(url = url, title = title)

internal fun PlanetData.toPlanet() = Planet(url = url, name = name)