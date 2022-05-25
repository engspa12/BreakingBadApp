package com.example.truelogicappchallenge.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.truelogicappchallenge.presentation.view.detail.DetailScreen
import com.example.truelogicappchallenge.presentation.view.main.MainScreen
import com.example.truelogicappchallenge.presentation.view.ui.theme.TruelogicAppChallengeTheme
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
            DetailScreen(
                "name",
                "nickname",
                "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg",
                true)
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
                MainScreen(navController = navController, listCharactersViewModel)
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
            ) { navBackStackEntry ->

                DetailScreen(
                    name = navBackStackEntry.arguments?.getString("name") ?: "",
                    nickname = navBackStackEntry.arguments?.getString("nickname") ?: "",
                    img = navBackStackEntry.arguments?.getString("img") ?: "",
                    isFavorite = navBackStackEntry.arguments?.getBoolean("isFavorite") ?: false,
                    modifier =  Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
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













