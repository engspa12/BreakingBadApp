package com.example.truelogicappchallenge.domain.repository

import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.ResponseData

interface ListCharactersRepository {
    suspend fun getListCharacters(): ResponseData<List<CharacterNetwork>>
}