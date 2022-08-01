package com.example.truelogicappchallenge.presentation.navigation

import com.example.truelogicappchallenge.presentation.model.CharacterView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object DetailScreen: Screen("detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgsDiffType(characterView: CharacterView): String {
        return buildString {
            val urlEncoded = URLEncoder.encode(characterView.img, StandardCharsets.UTF_8.toString())
            val partialString = withArgs(characterView.name, characterView.nickname, urlEncoded)
            append(partialString)
            append("/${characterView.isFavorite}")
        }
    }
}