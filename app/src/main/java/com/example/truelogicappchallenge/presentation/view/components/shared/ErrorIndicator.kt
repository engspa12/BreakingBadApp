package com.example.truelogicappchallenge.presentation.view.components.shared

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorIndicator(
    errorMessage: String,
    modifier: Modifier = Modifier
){
    Text(
        text = errorMessage,
        modifier = modifier,
        textAlign = TextAlign.Center)
}