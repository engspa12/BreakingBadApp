package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.data.database.dto.CharacterCacheMapper
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.data.network.responses.CharacterNetworkMapper
import com.example.truelogicappchallenge.domain.CacheMapper
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MapperModule {

    @Provides
    fun provideNetworkMapper(): NetworkMapper<CharacterNetwork, CharacterDomain> {
        return CharacterNetworkMapper()
    }

    @Provides
    fun provideCacheMapper(): CacheMapper<CharacterCache, CharacterDomain> {
        return CharacterCacheMapper()
    }
}