package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.util.StringWrapper

sealed class CharacterItemUIState(
    val value: CharacterView? = null,
    val errorMessage: StringWrapper = StringWrapper.SimpleString(""),
    val loadingMessage: StringWrapper = StringWrapper.SimpleString("")
) {
    class Success(value: CharacterView): CharacterItemUIState(value)
    class Error(errorMessage: StringWrapper): CharacterItemUIState(errorMessage = errorMessage)
    class Progress(loadingMessage: StringWrapper): CharacterItemUIState(loadingMessage = loadingMessage)
}