package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.usecase.GetItemDetailsUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.state.CharacterItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val handleFavoriteUseCase: HandleFavoritesUseCase,
    @DispatchersModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _itemUIState = MutableStateFlow<CharacterItemUIState>(CharacterItemUIState.Progress(""))
    val itemUIState: StateFlow<CharacterItemUIState> = _itemUIState

    fun getItemDetails(name: String) {
        showProgressBar()
        viewModelScope.launch(mainDispatcher) {
            //Only to see the ProgressBar in the UI
            delay(1000L)
            getItemDetailsUseCase.getItemDetails(name).collect { characterView ->
                _itemUIState.value = CharacterItemUIState.Success(characterView)
            }
        }
    }

    fun updateFavoriteFromDetail(name: String, isFavorite: Boolean){
        viewModelScope.launch(mainDispatcher) {
            handleFavoriteUseCase.updateFavoriteStatus(name, isFavorite)
        }
    }

    private fun showProgressBar() {
        _itemUIState.value = CharacterItemUIState.Progress("Loading item data...")
    }
}

