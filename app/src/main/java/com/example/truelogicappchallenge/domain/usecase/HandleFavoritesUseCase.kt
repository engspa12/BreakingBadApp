package com.example.truelogicappchallenge.domain.usecase

interface HandleFavoritesUseCase {
    suspend fun updateFavoriteStatus (name: String, isFavorite: Boolean)
}