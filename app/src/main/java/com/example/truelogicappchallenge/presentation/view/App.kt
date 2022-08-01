package com.example.truelogicappchallenge.presentation.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.truelogicappchallenge.presentation.navigation.Screen
import com.example.truelogicappchallenge.presentation.view.screens.demo.DemoScreen
import com.example.truelogicappchallenge.presentation.view.screens.detail.DetailScreen
import com.example.truelogicappchallenge.presentation.view.screens.main.MainScreen
import com.example.truelogicappchallenge.presentation.viewmodel.CharacterDetailsViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.example.truelogicappchallenge.presentation.viewmodel.SharedViewModel

@Composable
fun App() {

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

