package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.util.ResultWrapper

interface GetListCharactersUseCase {
    suspend fun getRepositoryData(): ResultWrapper<List<CharacterView>>
}