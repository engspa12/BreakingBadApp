package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getListCharactersUseCase: GetListCharactersUseCase,
    private val handleFavoriteUseCase: HandleFavoritesUseCase,
    @DispatchersModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _listCharacters = MutableLiveData<List<CharacterView>>()
    val listCharacters: LiveData<List<CharacterView>>
        get() = _listCharacters

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _progressBar = MutableLiveData<Boolean?>()
    val progressBar: LiveData<Boolean?>
        get() = _progressBar

    private val _emptyView = MutableLiveData<Boolean?>()
    val emptyView: LiveData<Boolean?>
        get() = _emptyView

    private var helperList: List<CharacterView> = listOf()

    fun getListCharacters(){

        showProgressBar(true)
        showEmptyView(false)

        viewModelScope.launch(mainDispatcher) {
            when(val data = getListCharactersUseCase.getRepositoryData()){
                is DataState.Success -> {
                    sendDataToView(data.value)
                    helperList = data.value
                }
                is DataState.Failure -> sendErrorMessage(data.errorMessage)
            }
        }
    }

    fun saveItemAsFavorite(position: Int){
        viewModelScope.launch(mainDispatcher) {
            val itemSelected = helperList[position]
            handleFavoriteUseCase.handleFavorite(itemSelected.name, itemSelected.isFavorite)
            getListCharacters()
        }

    }

    private fun sendDataToView(data: List<CharacterView>){
        _listCharacters.value = data
        showProgressBar(false)
        showEmptyView(false)
    }

    private fun sendErrorMessage(message: String?) {
        _errorMessage.value = message
        showProgressBar(false)
        showEmptyView(true)
    }

    private fun showEmptyView(show: Boolean){
        _emptyView.value = show
    }

    private fun showProgressBar(show: Boolean){
        _progressBar.value = show
    }
}