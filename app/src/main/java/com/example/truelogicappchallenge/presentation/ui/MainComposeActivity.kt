package com.example.truelogicappchallenge.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.presentation.ui.ui.theme.TruelogicAppChallengeTheme
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TruelogicAppChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CharactersList()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Daniel Bedoya",
            modifier = Modifier
                .background(Color.Blue)
                .weight(1f)
                .fillMaxWidth(),
            color = Color.White
        )
        Text(
            text = "Arni",
            modifier = Modifier
                .background(Color.Cyan)
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TruelogicAppChallengeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CharactersList()
        }
    }
}

@Composable
fun CharactersList(listCharactersViewModel: ListCharactersViewModel = viewModel()) {

    val list = listCharactersViewModel.listCharacters.observeAsState()

    LaunchedEffect(key1 = list) {
        listCharactersViewModel.getListCharacters()
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        itemsIndexed(list.value ?: listOf()) { index, characterView ->
            CharacterItem(
                characterView = CharacterView(
                    name = characterView.name,
                    nickname = characterView.nickname,
                    img = characterView.img,
                    isFavorite = characterView.isFavorite
                )
            ) {
                listCharactersViewModel.updateFavoriteStatus(index)
            }
        }
    }

}


@Composable
fun CharacterItem(characterView: CharacterView, callbackItem: () -> Unit) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = characterView.img,
            requestOptions = {
                RequestOptions()
                    .override(256, 256)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
            },
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .requiredSize(width = 50.dp, height = 80.dp)
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(start = 8.dp)
        ) {

            Text(
                text = characterView.name,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = characterView.nickname,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
        }
        Image(
            painter = if(characterView.isFavorite) painterResource(id = R.drawable.ic_favorite) else painterResource(id = R.drawable.ic_no_favorite),
            modifier = Modifier
                .requiredSize(36.dp)
                .weight(1f)
                .padding(end = 4.dp)
                .clickable {
                    callbackItem()
                },
            contentDescription = null
        )

    }
}