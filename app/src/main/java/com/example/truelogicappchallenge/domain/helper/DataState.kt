package com.example.truelogicappchallenge.domain.helper

sealed class DataState<T> {
    data class Success<T>(val value: T): DataState<T>()
    data class Failure<T>(val errorMessage: String?): DataState<T>()
}