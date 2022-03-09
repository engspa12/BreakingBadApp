package com.example.truelogicappchallenge.data.database

import androidx.room.*
import com.example.truelogicappchallenge.data.database.dto.CharacterCache

@Database(
    entities = [
        CharacterCache::class
    ], version = 1, exportSchema = false)
abstract class CharactersRoomDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

}

@Dao
interface CharactersDao{

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<CharacterCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(vararg characterCache: CharacterCache)

    @Query("UPDATE favorites SET isFavorite = :isFavorite WHERE characterName = :itemName")
    fun updateFavorite(itemName: String, isFavorite: Boolean)

}
