package com.jelletenbrinke.starwarsexplorer.data.di

import com.jelletenbrinke.starwarsexplorer.data.remote.StarWarsEndpoint
import com.jelletenbrinke.starwarsexplorer.data.repositories.StarWarsRepository
import com.jelletenbrinke.starwarsexplorer.data.repositories.StarWarsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRepository(starWarsEndpoint: StarWarsEndpoint) : StarWarsRepository {
        return StarWarsRepositoryImpl(starWarsEndpoint)
    }

    @Provides
    @Singleton
    fun provideEndpoint(retrofit: Retrofit): StarWarsEndpoint {
        return retrofit.create(StarWarsEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json {
            // Prevents crashing since we don't need all the fields for this assignment.
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl("https://swapi.py4e.com/api/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}