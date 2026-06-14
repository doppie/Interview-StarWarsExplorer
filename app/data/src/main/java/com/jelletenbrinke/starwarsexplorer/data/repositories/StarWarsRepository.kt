package com.jelletenbrinke.starwarsexplorer.data.repositories

import com.jelletenbrinke.starwarsexplorer.data.local.RequestResultsMemoryCache
import com.jelletenbrinke.starwarsexplorer.data.model.CharacterPageData
import com.jelletenbrinke.starwarsexplorer.data.model.FilmData
import com.jelletenbrinke.starwarsexplorer.data.model.PlanetData
import com.jelletenbrinke.starwarsexplorer.data.remote.StarWarsEndpoint
import javax.inject.Inject
import javax.inject.Singleton

interface StarWarsRepository {
    suspend fun getCharacterPage(pageUrl: String): Result<CharacterPageData>
    suspend fun getFilm(filmUrl: String): Result<FilmData>
    suspend fun getPlanet(planetUrl: String): Result<PlanetData>
}

@Singleton
class StarWarsRepositoryImpl @Inject constructor(
    private val endpoint: StarWarsEndpoint,
    private val pageCache: RequestResultsMemoryCache<CharacterPageData> = RequestResultsMemoryCache(),
    private val filmsCache: RequestResultsMemoryCache<FilmData> = RequestResultsMemoryCache(),
    private val planetsCache: RequestResultsMemoryCache<PlanetData> = RequestResultsMemoryCache(),
) : StarWarsRepository {

    override suspend fun getCharacterPage(pageUrl: String): Result<CharacterPageData> {
        // check if we can return a cached result.
        pageCache.get(pageUrl)?.let {
            return Result.success(it)
        }

        // request the page from the endpoint.
        return try {
            val response = endpoint.getCharacterPage(pageUrl)
            if (response.isSuccessful) {
                response.body()?.let {
                    // cache the character page and return it.
                    pageCache.put(pageUrl, it)
                    Result.success(it)
                } ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFilm(filmUrl: String): Result<FilmData> {
        // check if we can return a cached result.
        filmsCache.get(filmUrl)?.let {
            return Result.success(it)
        }

        // request the film from the endpoint.
        return try {
            val response = endpoint.getFilm(filmUrl)
            if (response.isSuccessful) {
                response.body()?.let {
                    // cache the film and return it.
                    filmsCache.put(filmUrl, it)
                    Result.success(it)
                } ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlanet(planetUrl: String): Result<PlanetData> {
        // check if we can return a cached result.
        planetsCache.get(planetUrl)?.let {
            return Result.success(it)
        }

        // request the film from the endpoint.
        return try {
            val response = endpoint.getPlanet(planetUrl)
            if (response.isSuccessful) {
                response.body()?.let {
                    // cache the plant and return it.
                    planetsCache.put(planetUrl, it)
                    Result.success(it)
                } ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}