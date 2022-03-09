package com.example.truelogicappchallenge.domain.model

data class CharacterDomain(
    var id: Int,
    var name : String,
    var nickname : String,
    var img : String,
    var isFavorite: Boolean = false
)