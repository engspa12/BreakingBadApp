package com.example.truelogicappchallenge.presentation.view.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.truelogicappchallenge.global.Screen
import com.example.truelogicappchallenge.presentation.state.CharactersListUIState
import com.example.truelogicappchallenge.presentation.view.components.CharactersListComponent
import com.example.truelogicappchallenge.presentation.view.components.ErrorTextComponent
import com.example.truelogicappchallenge.presentation.view.components.ProgressBarComponent
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel

@Composable
fun MainScreen(
    navController: NavController,
    listCharactersViewModel: ListCharactersViewModel
){

    val lazyState = rememberLazyListState()
    val uiState by listCharactersViewModel.mainUIState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        listCharactersViewModel.getListCharacters()
    }

    when(uiState) {
        is CharactersListUIState.Success -> {
            (uiState as CharactersListUIState.Success).data?.let { list ->
                CharactersListComponent(
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
            ProgressBarComponent(
                message = (uiState as CharactersListUIState.Progress).loadingMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is CharactersListUIState.Error -> {
            ErrorTextComponent(
                errorMessage = (uiState as CharactersListUIState.Error).error,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp))
        }
    }
}

