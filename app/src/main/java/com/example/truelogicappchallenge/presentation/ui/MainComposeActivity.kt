package com.example.truelogicappchallenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    MainContainer()
                }
            }
        }
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
            ErrorTextComponent("Redbee")
        }
    }
}

@Composable
fun MainContainer(listCharactersViewModel: ListCharactersViewModel = viewModel()){

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
        CharactersListComponent(lazyState, list, listCharactersViewModel)
    }

    if(emptyView == true && container == false && progressBar == false){
        ErrorTextComponent(errorMessage = errorMessage ?: "")
    }

    if(progressBar == true && container == false && emptyView == false){
        ProgressBarComponent()
    }
}

@Composable
fun ProgressBarComponent(){
    CircularProgressIndicator(
        modifier = Modifier.requiredSize(60.dp))
}

@Composable
fun ErrorTextComponent(errorMessage: String){
    Text(
        text = errorMessage,
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 20.dp),
        textAlign = TextAlign.Center)
}


@Composable
fun CharactersListComponent(lazyState: LazyListState, list: List<CharacterView>?, listCharactersViewModel: ListCharactersViewModel) {

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        state = lazyState
    ) {
        itemsIndexed(list ?: listOf()) { index, characterView ->
            CharacterItemComponent(
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
fun CharacterItemComponent(characterView: CharacterView, callbackItem: () -> Unit) {
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