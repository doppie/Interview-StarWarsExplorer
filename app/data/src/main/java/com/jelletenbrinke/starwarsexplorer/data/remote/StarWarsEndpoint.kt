package com.jelletenbrinke.starwarsexplorer.data.remote

import com.jelletenbrinke.starwarsexplorer.data.model.CharacterPageData
import com.jelletenbrinke.starwarsexplorer.data.model.FilmData
import com.jelletenbrinke.starwarsexplorer.data.model.PlanetData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Retrofit Interface for the Star Wars API at https://swapi.py4e.com/api/.
 *
 * Note: Limited to the necessary endpoints used in the app.
 * Note 2: It's common to specify the actual path for each endpoint in this interface,
 * but the data models directly reference URLs instead of identifiers so this is easier to work with.
 * The alternative would be to parse the URLs and strip parts, seemed like over-engineering.
 */
interface StarWarsEndpoint {
    // example url: https://swapi.py4e.com/api/people/?page=2
    @GET
    suspend fun getCharacterPage(@Url url: String): Response<CharacterPageData>

    // example url: https://swapi.py4e.com/api/films/2
    @GET
    suspend fun getFilm(@Url url: String): Response<FilmData>

    // example url: https://swapi.py4e.com/api/planet/7
    @GET
    suspend fun getPlanet(@Url url: String): Response<PlanetData>
}