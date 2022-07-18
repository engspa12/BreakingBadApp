package com.example.truelogicappchallenge.domain.repository

import com.example.truelogicappchallenge.util.ResultWrapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getListCharacters(): ResultWrapper<List<CharacterDomain>>
    suspend fun getCharacterDetails(name: String): Flow<CharacterDomain>
    suspend fun handleFavorite(nameFavoriteItem: String, isFavorite: Boolean)
}