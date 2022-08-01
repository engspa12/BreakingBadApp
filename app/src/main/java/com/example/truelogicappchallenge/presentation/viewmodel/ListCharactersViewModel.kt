package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.presentation.state.CharactersListUIState
import com.example.truelogicappchallenge.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getListCharactersUseCase: GetListCharactersUseCase,
    private val handleFavoriteUseCase: HandleFavoritesUseCase,
    @DispatchersModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _mainUIState = MutableStateFlow<CharactersListUIState>(CharactersListUIState.Progress(""))
    val mainUIState: StateFlow<CharactersListUIState>
        get() = _mainUIState

    private var helperList: List<CharacterView> = listOf()
    private var countingIdlingResource1: CountingIdlingResource? = null
    private var countingIdlingResource2: CountingIdlingResource? = null

    fun getListCharacters(){

        incrementIdlingResourceAPICall()
        showProgressBar()
        viewModelScope.launch(mainDispatcher) {
            //Only to see the ProgressBar in the UI
            delay(1000L)
            when(val result = getListCharactersUseCase.getRepositoryData()){
                is ResultWrapper.Success -> {
                    sendDataToView(result.value)
                    helperList = result.value
                    decrementIdlingResourceAPICall()
                }
                is ResultWrapper.Failure -> {
                    sendErrorMessage(result.errorMessage ?: "")
                    decrementIdlingResourceAPICall()
                }
            }
        }
    }

    fun updateFavoriteStatus(position: Int){
        incrementIdlingResourceFavoriteStatus()
        viewModelScope.launch(mainDispatcher) {
            val itemSelected = helperList[position]
            handleFavoriteUseCase.updateFavoriteStatus(itemSelected.name, itemSelected.isFavorite)
            getListCharacters()
            decrementIdlingResourceFavoriteStatus()
        }

    }

    private fun sendDataToView(data: List<CharacterView>){
        _mainUIState.value = CharactersListUIState.Success(data)
    }

    private fun sendErrorMessage(message: String) {
        _mainUIState.value = CharactersListUIState.Error(message)
    }

    private fun showProgressBar() {
        _mainUIState.value = CharactersListUIState.Progress("Loading list of items...")
    }

    private fun incrementIdlingResourceAPICall(){
        countingIdlingResource1?.increment()
    }

    private fun decrementIdlingResourceAPICall(){
        countingIdlingResource1?.decrement()
    }

    private fun incrementIdlingResourceFavoriteStatus(){
        countingIdlingResource2?.increment()
    }

    private fun decrementIdlingResourceFavoriteStatus(){
        countingIdlingResource2?.decrement()
    }

    @VisibleForTesting
    fun setIdlingResourceAPICall(countingIdlingResource: CountingIdlingResource){
        this.countingIdlingResource1 = countingIdlingResource
    }

    @VisibleForTesting
    fun setIdlingResourceUpdateFavorite(countingIdlingResource: CountingIdlingResource){
        this.countingIdlingResource2 = countingIdlingResource
    }
}