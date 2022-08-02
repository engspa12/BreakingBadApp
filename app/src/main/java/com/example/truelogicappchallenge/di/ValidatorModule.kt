package com.example.truelogicappchallenge.di

import android.content.Context
import com.example.truelogicappchallenge.util.Validator
import com.example.truelogicappchallenge.util.ValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ValidatorModule {

    @Provides
    fun provideValidator(@ApplicationContext appContext: Context): Validator {
        return ValidatorImpl(appContext)
    }
}