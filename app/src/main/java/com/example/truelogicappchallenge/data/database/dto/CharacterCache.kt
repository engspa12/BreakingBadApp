package com.example.truelogicappchallenge.data.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class CharacterCache(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val characterName: String,
    val isFavorite: Boolean
)