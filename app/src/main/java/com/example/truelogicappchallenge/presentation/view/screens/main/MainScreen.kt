package com.example.truelogicappchallenge.presentation.view.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.truelogicappchallenge.global.Constants
import com.example.truelogicappchallenge.presentation.navigation.Screen
import com.example.truelogicappchallenge.presentation.state.CharactersListUIState
import com.example.truelogicappchallenge.presentation.view.components.main.CharactersList
import com.example.truelogicappchallenge.presentation.view.components.shared.ErrorIndicator
import com.example.truelogicappchallenge.presentation.view.components.shared.ProgressBar
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.SharedViewModel

@Composable
fun MainScreen(
    navController: NavController,
    listCharactersViewModel: ListCharactersViewModel,
    sharedViewModel: SharedViewModel
) {
    val lazyState = rememberLazyListState()
    val uiState by listCharactersViewModel.mainUIState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        listCharactersViewModel.getListCharacters()
        sharedViewModel.setSharedValue(Constants.VALUE_FROM_MAIN)
    }

    when (uiState) {
        is CharactersListUIState.Success -> {
            CharactersList(
                lazyState,
                uiState.value,
                { characterView ->
                    navController.navigate(Screen.DetailScreen.withArgs(characterView.name))
                }
            )
            { index ->
                listCharactersViewModel.updateFavoriteStatus(index)
            }
        }
        is CharactersListUIState.Progress -> {
            ProgressBar(
                message = uiState.loadingMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is CharactersListUIState.Error -> {
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

