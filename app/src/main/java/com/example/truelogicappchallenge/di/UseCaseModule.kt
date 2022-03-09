package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCaseImpl
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetListCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetListCharactersUseCase {
        return GetListCharactersUseCaseImpl(charactersRepository)
    }

    @Provides
    fun provideSaveAsFavoriteUseCase(
        charactersRepository: CharactersRepository
    ): HandleFavoritesUseCase {
        return HandleFavoritesUseCaseImpl(charactersRepository)

    }
}