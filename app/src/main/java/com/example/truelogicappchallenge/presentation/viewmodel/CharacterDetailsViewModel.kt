package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.usecase.GetItemDetailsUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.CharacterItemUIState
import com.example.truelogicappchallenge.presentation.model.CharacterView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

