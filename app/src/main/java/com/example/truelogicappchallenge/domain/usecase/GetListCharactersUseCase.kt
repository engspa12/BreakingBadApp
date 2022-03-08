package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.presentation.model.CharacterView

interface GetListCharactersUseCase {
    suspend fun getRepositoryData(): DataState<List<CharacterView>>
}