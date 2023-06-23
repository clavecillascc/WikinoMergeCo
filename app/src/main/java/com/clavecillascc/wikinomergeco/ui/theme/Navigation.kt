package com.clavecillascc.wikinomergeco.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.screens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.screens.DictionaryScreen
import com.clavecillascc.wikinomergeco.screens.HomeScreen
import com.clavecillascc.wikinomergeco.screens.TranslateScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home"){
            HomeScreen()
        }
        composable(route = "translate"){
            TranslateScreen()
        }
        composable(route = "dictionary"){
            DictionaryScreen()
        }
        composable(route = "collaborator"){
            CollaboratorScreen()
        }
    }
}