package com.example.truelogicappchallenge.data.di

import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.repository.ListCharactersRepositoryImpl
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(
        serviceApi: ServiceApi
    ): ListCharactersRepository {
        return ListCharactersRepositoryImpl(serviceApi)
    }
}