package com.jelletenbrinke.starwarsexplorer.domain.usecases

import com.jelletenbrinke.starwarsexplorer.data.repositories.StarWarsRepository
import com.jelletenbrinke.starwarsexplorer.domain.model.CharacterPage
import com.jelletenbrinke.starwarsexplorer.domain.model.Film
import com.jelletenbrinke.starwarsexplorer.domain.model.toCharacterPage
import com.jelletenbrinke.starwarsexplorer.domain.model.toFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface FetchCharacterPageUseCase {
    fun fetchPage(pageUrl: String): Flow<Result<CharacterPage>>
}

class FetchCharacterPageUseCaseImpl @Inject constructor(
    private val repository: StarWarsRepository
) : FetchCharacterPageUseCase {

    override fun fetchPage(pageUrl: String): Flow<Result<CharacterPage>> = flow {
        val pageResult = repository.getCharacterPage(pageUrl)

        if (pageResult.isSuccess) {
            pageResult.getOrNull()?.let { page ->
                // Initial emission with no films yet.
                emit(Result.success(page.toCharacterPage(pageUrl, emptyMap())))

                val filmUrls = page.characters.flatMap { it.filmUrls }.distinct()
                val fetchedFilms = mutableMapOf<String, Film>()

                filmUrls.forEach { url ->
                    val filmResult = repository.getFilm(url)

                    if (filmResult.isSuccess) {
                        filmResult.getOrNull()?.let { filmData ->
                            fetchedFilms[filmData.url] = filmData.toFilm()
                            // Emit update with the films fetched so far.
                            emit(Result.success(page.toCharacterPage(pageUrl, fetchedFilms.toMap())))
                        }
                    } else {
                        emit(Result.failure(filmResult.exceptionOrNull() ?: Exception("Unknown error")))
                    }
                }
            }
        } else {
            emit(Result.failure(pageResult.exceptionOrNull() ?: Exception("Unknown error")))
        }
    }
}