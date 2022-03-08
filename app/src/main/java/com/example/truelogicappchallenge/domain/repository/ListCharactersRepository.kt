package com.example.truelogicappchallenge.domain.repository

import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain

interface ListCharactersRepository {
    suspend fun getListCharacters(): ResponseData<List<CharacterNetwork>>
}