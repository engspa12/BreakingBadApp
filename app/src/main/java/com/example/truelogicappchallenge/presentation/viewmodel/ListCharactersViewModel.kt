package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getListCharactersUseCase: GetListCharactersUseCase
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

    fun getListCharacters(){

        viewModelScope.launch(Dispatchers.Main) {
            when(val data = getListCharactersUseCase.getRepositoryData()){
                is DataState.Success -> sendDataToView(data.value)
                is DataState.Failure -> sendErrorMessage(data.errorMessage)
            }
        }

    }

    fun saveItemAsFavorite(position: Int){
        //Todo
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