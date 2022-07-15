package com.example.truelogicappchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IODispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainDispatcher
}