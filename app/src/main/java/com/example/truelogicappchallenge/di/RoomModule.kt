package com.example.truelogicappchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.database.CharactersRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): CharactersRoomDatabase {
        return Room.databaseBuilder(
            context,
            CharactersRoomDatabase::class.java,
            "CharacterDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: CharactersRoomDatabase): CharactersDao {
        return database.charactersDao()
    }
}