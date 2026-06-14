package com.jelletenbrinke.starwarsexplorer.domain.model

import com.jelletenbrinke.starwarsexplorer.data.model.CharacterData
import com.jelletenbrinke.starwarsexplorer.data.model.CharacterPageData
import com.jelletenbrinke.starwarsexplorer.data.model.FilmData

/**
 * DataModel to DomainModel Mapper utility extension functions.
 */

internal fun CharacterPageData.toCharacterPage(films: Map<String, Film>) = CharacterPage(
    nextPageUrl = nextPageUrl,
    characters = characters.map { it.toCharacter(films) },
)

internal fun CharacterData.toCharacter(films: Map<String, Film>) =
    Character(
        url = url,
        name = name,
        height = height,
        birthYear = birthYear,
        gender = gender,
        homeworldUrl = homeworldUrl,
        films = filmUrls.mapNotNull { films[it] }
    )

internal fun FilmData.toFilm() = Film(url = url, name = title)