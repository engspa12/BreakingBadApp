package com.example.truelogicappchallenge.data.di

import com.example.truelogicappchallenge.data.repository.ListCharactersRepositoryImpl
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesUseCase(
        listCharactersRepository: ListCharactersRepository
    ): GetListCharactersUseCase {
        return GetListCharactersUseCaseImpl(listCharactersRepository)
    }
}