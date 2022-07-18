package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.util.ResultWrapper
import javax.inject.Inject


class GetListCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : GetListCharactersUseCase {

    override suspend fun getRepositoryData(): ResultWrapper<List<CharacterView>> {

        return when(val repositoryData = charactersRepository.getListCharacters()){
                is ResultWrapper.Success -> {
                    val filteredList = repositoryData.value.sortedWith(compareBy({ !it.isFavorite }, { it.id }))
                    val originalList = filteredList.map { it.toView() }
                    ResultWrapper.Success(originalList)
                }
                is ResultWrapper.Failure -> {
                    ResultWrapper.Failure(repositoryData.errorMessage)
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