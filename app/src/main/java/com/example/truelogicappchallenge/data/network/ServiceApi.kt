package com.example.truelogicappchallenge.data.network

import com.example.truelogicappchallenge.data.network.response.CharacterNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("/api/characters")
    suspend fun getListCharacters(@Query("limit") limit: Int): List<CharacterNetwork>
}