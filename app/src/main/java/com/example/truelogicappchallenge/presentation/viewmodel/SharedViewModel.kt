package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _vmSharedValue = MutableStateFlow<String>("No value")
    val vmSharedValue: StateFlow<String> = _vmSharedValue

    fun setSharedValue(valueToPass: String) {
        _vmSharedValue.value = valueToPass
    }
}