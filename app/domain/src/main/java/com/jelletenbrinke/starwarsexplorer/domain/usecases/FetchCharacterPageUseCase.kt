package com.jelletenbrinke.starwarsexplorer.domain.usecases

import com.jelletenbrinke.starwarsexplorer.data.model.CharacterData
import com.jelletenbrinke.starwarsexplorer.data.repositories.StarWarsRepository
import com.jelletenbrinke.starwarsexplorer.domain.model.CharacterPage
import com.jelletenbrinke.starwarsexplorer.domain.model.Film
import com.jelletenbrinke.starwarsexplorer.domain.model.toCharacterPage
import com.jelletenbrinke.starwarsexplorer.domain.model.toFilm
import javax.inject.Inject

interface FetchCharacterPageUseCase {
    suspend fun fetchPage(pageUrl: String): Result<CharacterPage>
}

class FetchCharacterPageUseCaseImpl @Inject constructor(
    private val repository: StarWarsRepository
) : FetchCharacterPageUseCase {
    // TODO: this could be a Flow<Result<CharacterPage>.
    //  That would allow us to emit the page first, and later emit updates with the films we fetched.
    override suspend fun fetchPage(pageUrl: String): Result<CharacterPage> {
        val pageResult = repository.getCharacterPage(pageUrl)

        if (pageResult.isSuccess) {
            pageResult.getOrNull()?.let { page ->
                val films = fetchFilms(page.characters)

                return Result.success(page.toCharacterPage(films))
            }
        }
        return Result.failure(pageResult.exceptionOrNull() ?: Exception())
    }

    private suspend fun fetchFilms(characters: List<CharacterData>): Map<String, Film> {
        // get all the unique film urls for the characters.
        val filmUrls = characters.flatMap { it.filmUrls }.distinct()

        // keep track of the fetched films.
        val fetchedFilms = mutableMapOf<String, Film>()

        // fetch the film data for each url.
        filmUrls.forEach {
            val filmResult = repository.getFilm(it)

            // TODO: we don't handle errors here.
            if (filmResult.isSuccess) {
                filmResult.getOrNull()?.let { filmData ->
                    fetchedFilms[filmData.url] = filmData.toFilm()
                }
            }
        }

        return fetchedFilms
    }
}