package com.example.truelogicappchallenge.presentation.view.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.truelogicappchallenge.global.Screen
import com.example.truelogicappchallenge.presentation.view.compose.components.shared.ProgressBar
import com.example.truelogicappchallenge.presentation.view.compose.screens.detail.DetailScreen
import com.example.truelogicappchallenge.presentation.view.compose.screens.main.MainScreen
import com.example.truelogicappchallenge.presentation.view.compose.theme.BreakingBadAppTheme
import com.example.truelogicappchallenge.presentation.viewmodel.CharacterDetailsViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreakingBadAppTheme {
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
    BreakingBadAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProgressBar(
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
                route = Screen.MainScreen.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("characters")
                }
                val parentViewModel = hiltViewModel<SharedViewModel>(parentEntry)
                val listCharactersViewModel = hiltViewModel<ListCharactersViewModel>()
                MainScreen(
                    navController = navController, 
                    listCharactersViewModel =  listCharactersViewModel,
                    sharedViewModel = parentViewModel
                )
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
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("characters")
                }
                val parentViewModel = hiltViewModel<SharedViewModel>(parentEntry)
                val characterDetailsViewModel = hiltViewModel<CharacterDetailsViewModel>()
                DetailScreen(
                    name = backStackEntry.arguments?.getString("name") ?: "",
                    characterDetailsViewModel = characterDetailsViewModel,
                    sharedViewModel = parentViewModel
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













