package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.presentation.model.CharacterView
import kotlinx.coroutines.flow.Flow

interface GetItemDetailsUseCase {
    suspend fun getItemDetails(name: String): Flow<CharacterView>
}