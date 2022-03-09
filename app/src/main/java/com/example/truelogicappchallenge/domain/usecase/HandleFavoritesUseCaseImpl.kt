package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.repository.FavoritesRepository
import javax.inject.Inject

class HandleFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
): HandleFavoritesUseCase {

    override suspend fun getFavoriteItems(): DataState<List<CharacterCache>> {
        val favorites = favoritesRepository.getListFavorites()
        return DataState.Success(favorites)
    }

    override suspend fun setFavoriteItem(name: String) {
        favoritesRepository.saveFavorite(name)
    }

    override suspend fun deleteFavoriteItem(name: String) {
        favoritesRepository.deleteFavorite(name)
    }


}