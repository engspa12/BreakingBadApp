package com.example.truelogicappchallenge.data.database

import androidx.room.*
import com.example.truelogicappchallenge.data.database.dto.CharacterCache

@Database(
    entities = [
        CharacterCache::class
    ], version = 1)
abstract class CharactersRoomDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

}

@Dao
interface CharactersDao{

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<CharacterCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(vararg characterCache: CharacterCache)

    @Query("DELETE FROM favorites WHERE characterName = :item AND isFavorite = 1")
    fun deleteFavorite(item: String)

}
