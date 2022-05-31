package com.example.truelogicappchallenge.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.truelogicappchallenge.global.Screen
import com.example.truelogicappchallenge.presentation.view.components.ProgressBarComponent
import com.example.truelogicappchallenge.presentation.view.detail.DetailScreen
import com.example.truelogicappchallenge.presentation.view.main.MainScreen
import com.example.truelogicappchallenge.presentation.view.ui.theme.TruelogicAppChallengeTheme
import com.example.truelogicappchallenge.presentation.viewmodel.CharacterDetailsViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
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
                    AppScreen()
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
            ProgressBarComponent(
                message = "Hello Progress",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun AppScreen() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characters") {
        navigation(startDestination = Screen.MainScreen.route, route = "characters") {
            composable(
                route = Screen.MainScreen.route) {
                val listCharactersViewModel = hiltViewModel<ListCharactersViewModel>()
                MainScreen(
                    navController = navController, 
                    listCharactersViewModel =  listCharactersViewModel)
            }
            composable(
                route = Screen.DetailScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    }
                )
            ) { entry ->
                val characterDetailsViewModel = hiltViewModel<CharacterDetailsViewModel>()
                DetailScreen(
                    name = entry.arguments?.getString("name") ?: "",
                    characterDetailsViewModel = characterDetailsViewModel
                )
            }
        }
        navigation(startDestination = "demo_screen", route = "testgraph") {
            composable(
                route = "demo_screen"){
                DemoScreen()
            }
        }
    }
}

@Composable
fun DemoScreen(){
    Text(text = "Hello world")
}













