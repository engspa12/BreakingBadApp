package com.example.truelogicappchallenge.data.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carne_virtual")
data class CharacterCache(
    @PrimaryKey
    var id: Int,
    val characterName: String,
    val esAlumnoPregradoMatriculado: Boolean,
    val esAlumnoPregradoRetirado: Boolean,
    val errorDataAlumno: Boolean
)