package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView

sealed class CharactersListUIState(
    val value: List<CharacterView>? = null,
    val errorMessage: String = "",
    val loadingMessage: String = ""
) {
    class Success(value: List<CharacterView>): CharactersListUIState(value)
    class Error(errorMessage: String): CharactersListUIState(errorMessage = errorMessage)
    class Progress(loadingMessage: String): CharactersListUIState(loadingMessage = loadingMessage)
}