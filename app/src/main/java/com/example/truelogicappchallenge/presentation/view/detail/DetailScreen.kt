package com.example.truelogicappchallenge.presentation.view.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.presentation.state.CharacterItemUIState
import com.example.truelogicappchallenge.presentation.state.CharactersListUIState
import com.example.truelogicappchallenge.presentation.view.components.ErrorTextComponent
import com.example.truelogicappchallenge.presentation.view.components.ProgressBarComponent
import com.example.truelogicappchallenge.presentation.viewmodel.CharacterDetailsViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen(
    name: String,
    characterDetailsViewModel: CharacterDetailsViewModel) {

    val uiState by characterDetailsViewModel.itemUIState.collectAsState()
    
    LaunchedEffect(key1 = Unit) {
        characterDetailsViewModel.getItemDetails(name)
    }

    when(uiState) {
        is CharacterItemUIState.Success -> {
            (uiState as CharacterItemUIState.Success).data?.let { item ->
                DetailScreen(
                    name = item.name,
                    nickname = item.nickname,
                    img = item.img,
                    isFavorite = item.isFavorite,
                    {
                        characterDetailsViewModel.updateFavoriteFromDetail(item.name, item.isFavorite)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                )
            }
        }
        is CharacterItemUIState.Progress -> {
            ProgressBarComponent(
                message = (uiState as CharacterItemUIState.Progress).loadingMessage,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is CharacterItemUIState.Error -> {
            ErrorTextComponent(
                errorMessage = (uiState as CharacterItemUIState.Error).error,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp))
        }
    }
}

@Composable
fun DetailScreen(
    name: String,
    nickname: String,
    img: String,
    isFavorite: Boolean,
    buttonClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        GlideImage(
            imageModel = img,
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            },
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Green,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .requiredSize(width = 140.dp, height = 200.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Start,
                fontSize = 26.sp
            )
            Text(
                text = nickname,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if(isFavorite) Color.Red else Color.Blue
                ),
                onClick = {
                    buttonClick()
                }
            ) {
                Text(
                    text = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

