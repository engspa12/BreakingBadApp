package com.example.truelogicappchallenge.di

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
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

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