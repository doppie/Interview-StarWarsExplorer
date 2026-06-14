package com.jelletenbrinke.starwarsexplorer.domain.di

import com.jelletenbrinke.starwarsexplorer.domain.usecases.FetchCharacterPageUseCase
import com.jelletenbrinke.starwarsexplorer.domain.usecases.FetchCharacterPageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract interface UseCasesModule {

    @Binds
    fun bindFetchCharacterPageUseCase(impl: FetchCharacterPageUseCaseImpl): FetchCharacterPageUseCase
}