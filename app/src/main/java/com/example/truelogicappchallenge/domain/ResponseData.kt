package com.example.truelogicappchallenge.domain

sealed class ResponseData<out T> {
    data class Success<out T>(val value: T): ResponseData<T>()
    data class Failure(val errorMessage: String): ResponseData<Nothing>()
}