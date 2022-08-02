package com.example.truelogicappchallenge.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class StringWrapper {
    data class SimpleString(val value: String): StringWrapper()
    class ResourceString(@StringRes val id:Int, val args: Array<Any> = emptyArray()): StringWrapper()

    @Composable
    fun asString(): String {
        return when(this) {
            is SimpleString -> value
            is ResourceString -> stringResource(id = id, formatArgs =  args)
        }
    }

    fun getStringIdResource(): Int? {
        return when(this) {
            is SimpleString -> null
            is ResourceString -> id
        }
    }
}