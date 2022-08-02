package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.util.StringWrapper

sealed class CharactersListUIState(
    val value: List<CharacterView>? = null,
    val errorMessage: StringWrapper = StringWrapper.SimpleString(""),
    val loadingMessage: StringWrapper = StringWrapper.SimpleString("")
) {
    class Success(value: List<CharacterView>): CharactersListUIState(value)
    class Error(errorMessage: StringWrapper): CharactersListUIState(errorMessage = errorMessage)
    class Progress(loadingMessage: StringWrapper): CharactersListUIState(loadingMessage = loadingMessage)
}