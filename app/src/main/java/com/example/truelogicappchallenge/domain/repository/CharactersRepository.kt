package com.example.truelogicappchallenge.domain.repository

import com.example.truelogicappchallenge.data.helper.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getListCharacters(): ResponseData<List<CharacterDomain>>
    suspend fun getCharacterDetails(name: String): Flow<CharacterDomain>
    suspend fun handleFavorite(nameFavoriteItem: String, isFavorite: Boolean)
}