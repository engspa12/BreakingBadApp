package com.example.truelogicappchallenge.global.di

import com.example.truelogicappchallenge.data.network.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    companion object {
        private val BASE_URL = "https://www.breakingbadapi.com";
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Singleton
    @Provides
    fun provideNewsService(): ServiceApi {
        return provideRetrofit(BASE_URL).create(ServiceApi::class.java);
    }

}