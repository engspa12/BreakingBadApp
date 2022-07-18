package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.data.local.mapper.CharacterCacheMapper
import com.example.truelogicappchallenge.data.local.model.CharacterCache
import com.example.truelogicappchallenge.data.network.mapper.CharacterNetworkMapper
import com.example.truelogicappchallenge.data.network.model.CharacterNetwork
import com.example.truelogicappchallenge.data.util.CacheMapper
import com.example.truelogicappchallenge.data.util.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {

    @Provides
    fun provideNetworkMapper(): NetworkMapper<CharacterNetwork, CharacterDomain> {
        return CharacterNetworkMapper()
    }

    @Provides
    fun provideCacheMapper(): CacheMapper<CharacterCache, CharacterDomain> {
        return CharacterCacheMapper()
    }
}