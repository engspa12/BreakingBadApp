package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView

sealed class CharacterItemUIState(
    val value: CharacterView? = null,
    val errorMessage: String = "",
    val loadingMessage: String = ""
) {
    class Success(value: CharacterView): CharacterItemUIState(value)
    class Error(errorMessage: String): CharacterItemUIState(errorMessage = errorMessage)
    class Progress(loadingMessage: String): CharacterItemUIState(loadingMessage = loadingMessage)
}