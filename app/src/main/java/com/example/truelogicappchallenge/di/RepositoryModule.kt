package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.data.local.datasource.CharactersDao
import com.example.truelogicappchallenge.data.local.model.CharacterCache
import com.example.truelogicappchallenge.data.network.datasource.ServiceApi
import com.example.truelogicappchallenge.data.network.model.CharacterNetwork
import com.example.truelogicappchallenge.data.repository.CharactersRepositoryImpl
import com.example.truelogicappchallenge.data.util.CacheMapper
import com.example.truelogicappchallenge.data.util.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideNetworkRepository(
        serviceApi: ServiceApi,
        db: CharactersDao,
        networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>,
        cacheMapper: CacheMapper<CharacterCache, CharacterDomain>,
        @DispatchersModule.IODispatcher dispatcher: CoroutineDispatcher
    ): CharactersRepository {
        return CharactersRepositoryImpl(serviceApi, db, networkMapper, cacheMapper, dispatcher)
    }
}