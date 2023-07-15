package com.clavecillascc.wikinomergeco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.otherScreens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.otherScreens.HomeScreen
import com.clavecillascc.wikinomergeco.otherScreens.LibraryScreen
import com.clavecillascc.wikinomergeco.otherScreens.TranslateScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home"){
            HomeScreen()
        }
        composable(route = "translate"){
            TranslateScreen()
        }
        composable(route = "library"){
            LibraryScreen()
        }
        composable(route = "collaborator"){
            CollaboratorScreen()
        }
    }
}