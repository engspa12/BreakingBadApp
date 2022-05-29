package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    fun provideGetItemDetailsUseCase(
        charactersRepository: CharactersRepository
    ): GetItemDetailsUseCase {
        return GetItemDetailsUseCaseImpl(charactersRepository)
    }
}