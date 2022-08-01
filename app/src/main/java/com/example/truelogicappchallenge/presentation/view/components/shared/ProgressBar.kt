package com.example.truelogicappchallenge.presentation.view.components.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    message: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .requiredSize(50.dp)
        )
        Text(
            text = message,
            color = MaterialTheme.colors.primary,
            modifier =
            Modifier.padding(vertical = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}