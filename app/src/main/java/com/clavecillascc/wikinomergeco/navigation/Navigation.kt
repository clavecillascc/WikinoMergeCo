package com.clavecillascc.wikinomergeco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.libraryscreen.BicolanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.CebuanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.IlocanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.TextFileItemUI
import com.clavecillascc.wikinomergeco.otherScreens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.otherScreens.Languages
import com.clavecillascc.wikinomergeco.otherScreens.LibraryScreen
import com.clavecillascc.wikinomergeco.otherScreens.HomeScreen
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
            LibraryScreen(navController = navController)
        }
        composable(route = "collaborator"){
            CollaboratorScreen()
        }
        composable("cebuanoScreen") {
            CebuanoScreen(navController = navController)
        }
        composable("bicolanoScreen"){
            BicolanoScreen(navController = navController)
        }
        composable("ilocanoScreen"){
            IlocanoScreen(navController = navController)
        }
        composable("languages") {
            Languages(navController = navController)
        }
        composable("wordDetails/{content}") { backStackEntry ->
            val content = backStackEntry.arguments?.getString("content")
            content?.let {
                TextFileItemUI(it)
            }
        }
    }
}