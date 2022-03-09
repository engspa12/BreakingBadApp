package com.example.truelogicappchallenge.global.di

import android.content.SharedPreferences
import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.data.repository.FavoritesRepositoryImpl
import com.example.truelogicappchallenge.data.repository.ListCharactersRepositoryImpl
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.FavoritesRepository
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideNetworkRepository(
        serviceApi: ServiceApi,
        networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>
    ): ListCharactersRepository {
        return ListCharactersRepositoryImpl(serviceApi, networkMapper)
    }

    @Provides
    fun provideFavoriteRepository(
        db: CharactersDao
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(db)
    }
}