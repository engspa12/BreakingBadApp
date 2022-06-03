package com.example.truelogicappchallenge.di

import com.example.truelogicappchallenge.data.network.ServiceApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    companion object {
        private val BASE_URL = "https://www.breakingbadapi.com";
    }

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, mosh: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(mosh))
            .build();
    }

    @Singleton
    @Provides
    fun provideNewsService(mosh: Moshi): ServiceApi {
        return provideRetrofit(BASE_URL, mosh).create(ServiceApi::class.java);
    }

}