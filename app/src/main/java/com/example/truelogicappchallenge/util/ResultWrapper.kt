package com.example.truelogicappchallenge.util

sealed class ResultWrapper<T>{
    data class Success<T>(val value: T): ResultWrapper<T>()
    data class Failure<T>(val errorMessage: String? = null): ResultWrapper<T>()
}