package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.DataState
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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

    private val _container = MutableLiveData<Boolean?>()
    val container: LiveData<Boolean?>
        get() = _container

    private var helperList: List<CharacterView> = listOf()
    private var countingIdlingResource: CountingIdlingResource? = null

    fun getListCharacters(){

        incrementIdlingResource()
        showProgressBar(true)
        showEmptyView(false)
        showCharactersList(false)

        viewModelScope.launch(mainDispatcher) {

            when(val data = getListCharactersUseCase.getRepositoryData()){
                is DataState.Success -> {
                    sendDataToView(data.value)
                    helperList = data.value
                    decrementIdlingResouce()
                }
                is DataState.Failure -> {
                    sendErrorMessage(data.errorMessage)
                    decrementIdlingResouce()
                }
            }
        }
    }

    fun updateFavoriteStatus(position: Int){
        viewModelScope.launch(mainDispatcher) {
            val itemSelected = helperList[position]
            handleFavoriteUseCase.updateFavoriteStatus(itemSelected.name, itemSelected.isFavorite)
            getListCharacters()
        }

    }

    private fun sendDataToView(data: List<CharacterView>){
        _listCharacters.value = data
        showProgressBar(false)
        showEmptyView(false)
        showCharactersList(true)
    }

    private fun sendErrorMessage(message: String?) {
        _errorMessage.value = message
        showProgressBar(false)
        showEmptyView(true)
        showCharactersList(false)
    }

    private fun showEmptyView(show: Boolean){
        _emptyView.value = show
    }

    private fun showProgressBar(show: Boolean){
        _progressBar.value = show
    }

    private fun showCharactersList(show: Boolean){
        _container.value = show
    }

    fun incrementIdlingResource(){
        countingIdlingResource?.increment()
    }

    fun decrementIdlingResouce(){
        countingIdlingResource?.decrement()
    }

    @VisibleForTesting
    fun setIdlingResource(countingIdlingResource: CountingIdlingResource){
        this.countingIdlingResource = countingIdlingResource
    }
}