package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.presentation.model.CharacterView
import kotlinx.coroutines.flow.Flow

interface GetItemDetailsUseCase {
    suspend fun getItemDetails(id: Int): Flow<CharacterView>
}