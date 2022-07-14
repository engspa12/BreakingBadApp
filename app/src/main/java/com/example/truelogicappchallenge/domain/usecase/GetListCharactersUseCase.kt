package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.domain.helper.ResultDomain
import com.example.truelogicappchallenge.presentation.model.CharacterView

interface GetListCharactersUseCase {
    suspend fun getRepositoryData(): ResultDomain<List<CharacterView>>
}