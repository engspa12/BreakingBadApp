package com.example.truelogicappchallenge.presentation.model

import com.google.gson.annotations.SerializedName

data class CharacterView(
    var name : String,
    var nickname : String,
    var img : String,
    var isFavorite: Boolean
)