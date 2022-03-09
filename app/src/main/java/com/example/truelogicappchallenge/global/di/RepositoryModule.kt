package com.example.truelogicappchallenge.global.di

import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.data.repository.CharactersRepositoryImpl
import com.example.truelogicappchallenge.domain.CacheMapper
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
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
        db: CharactersDao,
        networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>,
        cacheMapper: CacheMapper<CharacterCache, CharacterDomain>
    ): CharactersRepository {
        return CharactersRepositoryImpl(serviceApi, db, networkMapper, cacheMapper)
    }
}