package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.truelogicappchallenge.global.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _vmSharedValue = MutableStateFlow<String>(Constants.NO_VALUE)
    val vmSharedValue: StateFlow<String> = _vmSharedValue

    fun setSharedValue(valueToPass: String) {
        _vmSharedValue.value = valueToPass
    }
}