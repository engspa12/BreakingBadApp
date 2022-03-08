package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import com.example.truelogicappchallenge.presentation.model.CharacterView
import javax.inject.Inject

class GetListCharactersUseCaseImpl @Inject constructor(
    private val listCharactersRepository: ListCharactersRepository
) : GetListCharactersUseCase {

    override suspend fun getRepositoryData(): DataState<List<CharacterView>> {

        return when(val data = listCharactersRepository.getListCharacters()){
            is ResponseData.Success -> {
                DataState.Success(data.value.map { it.toView() })
            }
            is ResponseData.Failure -> {
                DataState.Failure("Error")
            }
        }
    }
}

fun CharacterNetwork.toView(): CharacterView {

    val name = this.name ?: "No name"
    val nickname = this.nickname ?: "No nickname"
    val img = this.img ?: ""
    val favorite = false

    return CharacterView(
        name, nickname, img, favorite
    )
}