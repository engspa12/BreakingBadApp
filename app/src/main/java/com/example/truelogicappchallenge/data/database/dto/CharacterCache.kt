package com.example.truelogicappchallenge.data.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class CharacterCache(
    @PrimaryKey
    var id: Int = 0,
    val characterName: String,
    val characterNickname: String,
    val urlImg: String,
    val isFavorite: Boolean
)