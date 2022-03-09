package com.example.truelogicappchallenge.global.di

import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import com.example.truelogicappchallenge.domain.repository.FavoritesRepository
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
        listCharactersRepository: ListCharactersRepository
    ): GetListCharactersUseCase {
        return GetListCharactersUseCaseImpl(listCharactersRepository)
    }

    @Provides
    fun provideSaveAsFavoriteUseCase(
        savedItemsRepository: FavoritesRepository
    ): HandleFavoritesUseCase {
        return HandleFavoritesUseCaseImpl(savedItemsRepository)

    }
}