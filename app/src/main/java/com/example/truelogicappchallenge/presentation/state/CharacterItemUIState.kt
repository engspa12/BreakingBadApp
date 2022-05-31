package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView

sealed class CharacterItemUIState {
    data class Success(val data: CharacterView?): CharacterItemUIState()
    data class Error(val error: String): CharacterItemUIState()
    data class Progress(val loadingMessage: String): CharacterItemUIState()
}