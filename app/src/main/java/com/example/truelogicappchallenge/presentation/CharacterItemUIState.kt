package com.example.truelogicappchallenge.presentation

import com.example.truelogicappchallenge.presentation.model.CharacterView

sealed class CharacterItemUIState {
    data class Success(val itemData: CharacterView?): CharacterItemUIState()
    data class Error(val error: String): CharacterItemUIState()
}