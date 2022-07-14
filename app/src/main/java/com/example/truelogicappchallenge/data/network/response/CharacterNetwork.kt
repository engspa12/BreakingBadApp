package com.example.truelogicappchallenge.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterNetwork (

    @Json(name = "char_id")
    var charId: Int? = null,
    @Json(name = "name")
    var name : String? = null,
    @Json(name = "birthday")
    var birthday : String? = null,
    @Json(name ="occupation")
    var occupation : List<String> = arrayListOf(),
    @Json(name = "img")
    var img : String? = null,
    @Json(name = "status")
    var status : String? = null,
    @Json(name = "nickname")
    var nickname : String? = null,
    @Json(name = "appearance")
    var appearance : List<Int> = arrayListOf(),
    @Json(name = "portrayed")
    var portrayed : String? = null,
    @Json(name = "category")
    var category : String? = null,
    @Json(name = "better_call_saul_appearance")
    var betterCallSaulAppearance : List<String> = arrayListOf()

)