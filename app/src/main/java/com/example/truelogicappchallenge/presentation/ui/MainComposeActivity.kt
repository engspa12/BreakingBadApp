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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.global.MainScreen
import com.example.truelogicappchallenge.global.Navigation
import com.example.truelogicappchallenge.global.Screen
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
                    //MainContainer()
                    //Navigation()
                    MainWithNavigation()
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
            //Navigation()
            //ErrorTextComponent("Redbee")
            DetailScreenComponent(
                "name",
                "nickname",
                "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg",
                true)
            /*CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .requiredSize(60.dp))*/
        }
    }
}

@Composable
fun MainWithNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route) {
            val listCharactersViewModel = hiltViewModel<ListCharactersViewModel>()
            MainContainer(navController = navController, listCharactersViewModel)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}/{nickname}/{img}/{isFavorite}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("nickname") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("img") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("isFavorite") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                }
            )
        ) {
            navBackStackEntry ->
            DetailScreenComponent(
                name = navBackStackEntry.arguments?.getString("name") ?: "",
                nickname = navBackStackEntry.arguments?.getString("nickname") ?: "",
                img = navBackStackEntry.arguments?.getString("img") ?: "",
                isFavorite = navBackStackEntry.arguments?.getBoolean("isFavorite") ?: false
            )
        }

    }
}

@Composable
fun DetailScreenComponent(name: String, nickname: String, img: String, isFavorite: Boolean){
    /*Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = name, textAlign = TextAlign.Center)
            Text(text = nickname, textAlign = TextAlign.Center)
            Text(text = img, textAlign = TextAlign.Center)
            Text(text = "The flag is $isFavorite", textAlign = TextAlign.Center)
        }
    }*/
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        GlideImage(
            imageModel = img,
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            },
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
                    backgroundColor = if(isFavorite) Companion.Red else Companion.Blue
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MainContainer(navController: NavController, listCharactersViewModel: ListCharactersViewModel){

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
        CharactersListComponent(navController, lazyState, list, listCharactersViewModel)
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
        modifier = Modifier
            .fillMaxSize()
            .requiredSize(60.dp)
            .wrapContentHeight(Alignment.CenterVertically))
}

@Composable
fun ErrorTextComponent(errorMessage: String){
    Text(
        text = errorMessage,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 20.dp),
        textAlign = TextAlign.Center)
}


@Composable
fun CharactersListComponent(navController: NavController, lazyState: LazyListState, list: List<CharacterView>?, listCharactersViewModel: ListCharactersViewModel) {

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
                ), {
                    navController.navigate(Screen.DetailScreen.withArgsDiffType(characterView))
                }
            ) {
                listCharactersViewModel.updateFavoriteStatus(index)
            }
        }
    }

}


@Composable
fun CharacterItemComponent(characterView: CharacterView, navCallback: () -> Unit , callbackItem: () -> Unit) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .clickable {
                navCallback()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = characterView.img,
            requestOptions = {
                RequestOptions()
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
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = characterView.nickname,
                textAlign = TextAlign.Start,
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