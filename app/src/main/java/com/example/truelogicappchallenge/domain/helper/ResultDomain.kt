package com.example.truelogicappchallenge.domain.helper

sealed class ResultDomain<T> {
    data class Success<T>(val value: T): ResultDomain<T>()
    data class Failure<T>(val errorMessage: String?): ResultDomain<T>()
}