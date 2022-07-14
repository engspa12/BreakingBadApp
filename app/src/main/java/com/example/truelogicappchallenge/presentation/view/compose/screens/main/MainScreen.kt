package com.example.truelogicappchallenge.presentation.view.compose.screens.main

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
import com.example.truelogicappchallenge.global.Screen
import com.example.truelogicappchallenge.presentation.state.CharactersListUIState
import com.example.truelogicappchallenge.presentation.view.compose.components.main.CharactersList
import com.example.truelogicappchallenge.presentation.view.compose.components.shared.ErrorIndicator
import com.example.truelogicappchallenge.presentation.view.compose.components.shared.ProgressBar
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.SharedViewModel

@Composable
fun MainScreen(
    navController: NavController,
    listCharactersViewModel: ListCharactersViewModel,
    sharedViewModel: SharedViewModel
){
    val lazyState = rememberLazyListState()
    val uiState by listCharactersViewModel.mainUIState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        listCharactersViewModel.getListCharacters()
        println("The viewmodel is $sharedViewModel")
        sharedViewModel.setSharedValue("Value from Main")
    }

    when(uiState) {
        is CharactersListUIState.Success -> {
            (uiState as CharactersListUIState.Success).data?.let { list ->
                CharactersList(
                    lazyState,
                    list,
                    { characterView ->
                        navController.navigate(Screen.DetailScreen.withArgs(characterView.name))
                    }
                )
                { index ->
                    listCharactersViewModel.updateFavoriteStatus(index)
                }
            }
        }
        is CharactersListUIState.Progress -> {
            ProgressBar(
                message = (uiState as CharactersListUIState.Progress).loadingMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is CharactersListUIState.Error -> {
            ErrorIndicator(
                errorMessage = (uiState as CharactersListUIState.Error).errorMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

