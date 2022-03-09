package com.example.truelogicappchallenge.data.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_favorites")
data class CharacterCache(
    @PrimaryKey
    var id: Int,
    val characterName: String,
    val isFavorite: Boolean
)