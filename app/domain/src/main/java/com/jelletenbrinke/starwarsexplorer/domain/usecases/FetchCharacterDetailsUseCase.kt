package com.jelletenbrinke.starwarsexplorer.domain.usecases

import com.jelletenbrinke.starwarsexplorer.data.repositories.StarWarsRepository
import com.jelletenbrinke.starwarsexplorer.domain.model.Character
import com.jelletenbrinke.starwarsexplorer.domain.model.toCharacter
import com.jelletenbrinke.starwarsexplorer.domain.model.toPlanet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface FetchCharacterDetailsUseCase {
    fun fetchDetails(characterUrl: String): Flow<Result<Character>>
}

class FetchCharacterDetailsUseCaseImpl @Inject constructor(
    private val repository: StarWarsRepository
): FetchCharacterDetailsUseCase {
    override fun fetchDetails(characterUrl: String): Flow<Result<Character>> = flow {
        val characterResult = repository.getCharacter(characterUrl)

        if (characterResult.isSuccess) {
            characterResult.getOrNull()?.let { character ->
                // Initial emission with no homeworld yet.
                emit(Result.success(character.toCharacter(emptyMap())))

                // Fetch additional details: the homeworld (aka planet)
                val planetResult = repository.getPlanet(character.homeworldUrl)
                if (planetResult.isSuccess) {
                    planetResult.getOrNull()?.let { planetData ->
                        emit(Result.success(
                            character.toCharacter(planets = mapOf(planetData.url to planetData.toPlanet()))))
                    }
                } else {
                    emit(Result.failure(planetResult.exceptionOrNull() ?: Exception("Unknown error")))
                }
            }
        } else {
            emit(Result.failure(characterResult.exceptionOrNull() ?: Exception("Unknown error")))
        }
    }
}