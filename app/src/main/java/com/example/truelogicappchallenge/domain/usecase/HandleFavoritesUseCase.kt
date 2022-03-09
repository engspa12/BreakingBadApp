package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.presentation.model.CharacterView

interface HandleFavoritesUseCase {
    suspend fun getFavoriteItems(): DataState<List<CharacterCache>>
    suspend fun setFavoriteItem(name: String)
    suspend fun deleteFavoriteItem(name: String)
}