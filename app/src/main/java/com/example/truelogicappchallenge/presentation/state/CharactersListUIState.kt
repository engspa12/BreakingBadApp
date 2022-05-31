package com.example.truelogicappchallenge.presentation.state

import com.example.truelogicappchallenge.presentation.model.CharacterView

sealed class CharactersListUIState {
    data class Success(val data: List<CharacterView>?): CharactersListUIState()
    data class Error(val error: String): CharactersListUIState()
    data class Progress(val loadingMessage: String): CharactersListUIState()
}