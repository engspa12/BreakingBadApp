package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import javax.inject.Inject

class HandleFavoritesUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
): HandleFavoritesUseCase {

    override suspend fun updateFavoriteStatus(name: String, isFavorite: Boolean) {
        charactersRepository.handleFavorite(name, isFavorite)
    }
}