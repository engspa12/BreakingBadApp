package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.presentation.model.CharacterView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetItemDetailsUseCaseImpl(
    private val charactersRepository: CharactersRepository
): GetItemDetailsUseCase {

    override suspend fun getItemDetails(id: Int): Flow<CharacterView> {
        return charactersRepository.getCharacterDetails(id).map {
            it.toView()
        }
    }
}