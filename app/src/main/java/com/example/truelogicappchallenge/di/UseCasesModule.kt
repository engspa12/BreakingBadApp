package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.domain.usecase.*
import com.example.truelogicappchallenge.util.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetListCharactersUseCase(
        charactersRepository: CharactersRepository,
        validator: Validator
    ): GetListCharactersUseCase {
        return GetListCharactersUseCaseImpl(charactersRepository, validator)
    }

    @Provides
    fun provideSaveAsFavoriteUseCase(
        charactersRepository: CharactersRepository
    ): HandleFavoritesUseCase {
        return HandleFavoritesUseCaseImpl(charactersRepository)
    }

    @Provides
    fun provideGetItemDetailsUseCase(
        charactersRepository: CharactersRepository
    ): GetItemDetailsUseCase {
        return GetItemDetailsUseCaseImpl(charactersRepository)
    }
}