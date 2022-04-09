package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.presentation.model.CharacterView
import javax.inject.Inject


class GetListCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : GetListCharactersUseCase {

    override suspend fun getRepositoryData(): DataState<List<CharacterView>> {

        return when(val data = charactersRepository.getListCharacters()){
                is ResponseData.Success -> {
                    val filteredList = data.value.sortedWith(compareBy({ !it.isFavorite }, { it.id }))
                    val originalList = filteredList.map { it.toView() }
                    DataState.Success(originalList)
                }
                is ResponseData.Failure -> {
                    DataState.Failure(data.errorMessage)
                }
        }
    }
}

fun CharacterDomain.toView(): CharacterView {
    val name = this.name
    val nickname = this.nickname
    val img = this.img
    val isFavorite = this.isFavorite

    return CharacterView(
        name, nickname, img, isFavorite
    )
}