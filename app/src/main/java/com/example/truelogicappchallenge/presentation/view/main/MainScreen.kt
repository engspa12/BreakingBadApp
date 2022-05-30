package com.example.truelogicappchallenge.presentation.view.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.truelogicappchallenge.global.Screen
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

    val list by listCharactersViewModel.listCharacters.observeAsState()
    val progressBar by listCharactersViewModel.progressBar.observeAsState()
    val emptyView by listCharactersViewModel.emptyView.observeAsState()
    val errorMessage by listCharactersViewModel.errorMessage.observeAsState()
    val container by listCharactersViewModel.container.observeAsState()

    LaunchedEffect(key1 = Unit) {
        listCharactersViewModel.getListCharacters()
    }

    if(container == true && emptyView == false && progressBar == false) {
        CharactersListComponent(
            lazyState,
            list,
            { characterView ->
                navController.navigate(Screen.DetailScreen.withArgs(characterView.name))
                //navController.navigate("testgraph")
            }
        )
        { index ->
                listCharactersViewModel.updateFavoriteStatus(index)
        }
    }

    if(emptyView == true && container == false && progressBar == false){
        ErrorTextComponent(
            errorMessage = errorMessage ?: "",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(horizontal = 20.dp))
    }

    if(progressBar == true && container == false && emptyView == false){
        ProgressBarComponent(
            modifier = Modifier
                .fillMaxSize()
                .requiredSize(60.dp)
                .wrapContentHeight(Alignment.CenterVertically))
    }
}

