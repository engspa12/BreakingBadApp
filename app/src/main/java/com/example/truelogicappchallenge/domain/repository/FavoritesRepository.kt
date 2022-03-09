package com.example.truelogicappchallenge.domain.repository

import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain

interface FavoritesRepository {
    suspend fun getListFavorites(): List<CharacterCache>
    suspend fun saveFavorite(nameFavoriteItem: String)
    suspend fun deleteFavorite(nameFavoriteItem: String)
}