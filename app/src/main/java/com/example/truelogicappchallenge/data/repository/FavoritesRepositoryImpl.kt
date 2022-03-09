package com.example.truelogicappchallenge.data.repository

import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val db: CharactersDao
): FavoritesRepository {

    override suspend fun getListFavorites(): List<CharacterCache> {
        return db.getFavorites()
    }

    override suspend fun saveFavorite(nameFavoriteItem: String) {
        val favoriteItem = CharacterCache(characterName = nameFavoriteItem, isFavorite = true)
        db.insertFavorite(favoriteItem)
    }

    override suspend fun deleteFavorite(nameFavoriteItem: String) {
        db.deleteFavorite(nameFavoriteItem)
    }

}