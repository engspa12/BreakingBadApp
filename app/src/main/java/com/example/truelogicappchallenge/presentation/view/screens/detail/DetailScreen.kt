package com.example.truelogicappchallenge.presentation.view.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.truelogicappchallenge.global.Constants
import com.example.truelogicappchallenge.presentation.state.CharacterItemUIState
import com.example.truelogicappchallenge.presentation.view.components.detail.CharacterDetails
import com.example.truelogicappchallenge.presentation.view.components.shared.ErrorIndicator
import com.example.truelogicappchallenge.presentation.view.components.shared.ProgressBar
import com.example.truelogicappchallenge.presentation.viewmodel.CharacterDetailsViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.SharedViewModel

@Composable
fun DetailScreen(
    name: String,
    characterDetailsViewModel: CharacterDetailsViewModel,
    sharedViewModel: SharedViewModel
){
    val uiState by characterDetailsViewModel.itemUIState.collectAsState()
    val sharedValue by sharedViewModel.vmSharedValue.collectAsState()

    LaunchedEffect(key1 = Unit) {
        characterDetailsViewModel.getItemDetails(name)
    }

    when (uiState) {
        is CharacterItemUIState.Success -> {
            uiState.value?.let { item ->
                when (sharedValue) {
                    Constants.VALUE_FROM_MAIN -> {
                        CharacterDetails(
                            name = item.name,
                            nickname = item.nickname,
                            img = item.img,
                            isFavorite = item.isFavorite,
                            {
                                characterDetailsViewModel.updateFavoriteFromDetail(
                                    item.name,
                                    item.isFavorite
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        )
                    }
                    Constants.VALUE_FROM_OTHERS -> {
                        Text(
                            text = sharedValue,
                            modifier = Modifier.fillMaxSize().background(Color.Yellow))
                    }
                    else -> {
                        Text(
                            text = sharedValue,
                            modifier = Modifier.fillMaxSize().background(Color.Magenta))
                    }
                }
            }
        }
        is CharacterItemUIState.Progress -> {
            ProgressBar(
                message = uiState.loadingMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is CharacterItemUIState.Error -> {
            ErrorIndicator(
                errorMessage = uiState.errorMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

