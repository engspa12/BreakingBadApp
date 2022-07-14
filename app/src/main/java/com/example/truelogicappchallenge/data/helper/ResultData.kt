package com.example.truelogicappchallenge.data.helper

sealed class ResultData<out T>() {
    data class Success<out T>(val value: T): ResultData<T>()
    data class Failure(val errorMessage: String): ResultData<Nothing>()
}