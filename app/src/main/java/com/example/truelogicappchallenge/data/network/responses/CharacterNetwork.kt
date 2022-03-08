package com.example.truelogicappchallenge.data.network.responses

import com.google.gson.annotations.SerializedName

data class CharacterNetwork (

    @SerializedName("char_id")
    var charId: Int? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("birthday")
    var birthday : String? = null,
    @SerializedName("occupation")
    var occupation : List<String> = arrayListOf(),
    @SerializedName("img")
    var img : String? = null,
    @SerializedName("status")
    var status : String? = null,
    @SerializedName("nickname")
    var nickname : String? = null,
    @SerializedName("appearance")
    var appearance : List<Int> = arrayListOf(),
    @SerializedName("portrayed")
    var portrayed : String? = null,
    @SerializedName("category")
    var category : String? = null,
    @SerializedName("better_call_saul_appearance")
    var betterCallSaulAppearance : List<String> = arrayListOf()

)