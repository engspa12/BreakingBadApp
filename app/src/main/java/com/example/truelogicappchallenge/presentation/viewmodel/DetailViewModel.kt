package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.usecase.GetItemDetailsUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.CharacterItemUIState
import com.example.truelogicappchallenge.presentation.model.CharacterView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val handleFavoriteUseCase: HandleFavoritesUseCase,
    @DispatchersModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _itemUIState = MutableStateFlow(CharacterItemUIState.Success(null))
    val itemUIState: StateFlow<CharacterItemUIState>
        get() = _itemUIState

    fun getItemDetails(id: Int) {
        viewModelScope.launch(mainDispatcher) {
            getItemDetailsUseCase.getItemDetails(id).collect { characterView ->
                _itemUIState.value = CharacterItemUIState.Success(characterView)
            }
        }
    }

    fun updateFavoriteFromDetail(name: String, isFavorite: Boolean){
        viewModelScope.launch(mainDispatcher) {
            handleFavoriteUseCase.updateFavoriteStatus(name, isFavorite)
        }
    }
}

