package com.example.truelogicappchallenge.domain.usecase

import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.domain.util.toView
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.util.ResultWrapper
import com.example.truelogicappchallenge.util.StringWrapper
import com.example.truelogicappchallenge.util.Validator
import javax.inject.Inject

class GetListCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val validator: Validator
) : GetListCharactersUseCase {

    override suspend fun getRepositoryData(): ResultWrapper<List<CharacterView>> {

        return when(val repositoryData = charactersRepository.getListCharacters()){
                is ResultWrapper.Success -> {
                    val filteredList = repositoryData.value.sortedWith(compareBy({ !it.isFavorite }, { it.id }))
                    val originalList = filteredList.map { it.toView() }
                    ResultWrapper.Success(originalList)
                }
                is ResultWrapper.Failure -> {
                    if(validator.isOnline()){
                        ResultWrapper.Failure(repositoryData.errorMessage)
                    } else {
                        ResultWrapper.Failure(StringWrapper.ResourceString(R.string.no_internet_connection))
                    }
                }
        }
    }
}