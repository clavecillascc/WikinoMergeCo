package com.clavecillascc.wikinomergeco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.libraryscreen.BicolanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.CebuanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.IlocanoScreen
import com.clavecillascc.wikinomergeco.libraryscreen.TextFileItemUI
import com.clavecillascc.wikinomergeco.collaboratorscreen.AddCollaboratorScreen
import com.clavecillascc.wikinomergeco.otherScreens.AboutScreen
import com.clavecillascc.wikinomergeco.otherScreens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.otherScreens.Languages
import com.clavecillascc.wikinomergeco.otherScreens.LibraryScreen
import com.clavecillascc.wikinomergeco.otherScreens.HomeScreen
import com.clavecillascc.wikinomergeco.otherScreens.TranslateScreen
import com.clavecillascc.wikinomergeco.signin.UserData


@Composable
fun Navigation(navController: NavHostController, userData: UserData?) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(navController)
        }
        composable(route = "translate") {
            TranslateScreen(navController)
        }
        composable(route = "library") {
            LibraryScreen(navController)
        }
        composable(route = "collaborator") {
            CollaboratorScreen(navController, userData)
        }
        composable(route = "about") {
            AboutScreen(navController)
        }
        composable("cebuanoScreen") {
            CebuanoScreen(navController)
        }
        composable("bicolanoScreen") {
            BicolanoScreen(navController)
        }
        composable("ilocanoScreen") {
            IlocanoScreen(navController)
        }
        composable("languages") {
            Languages(navController)
        }
        composable("addCollaborator") {
            AddCollaboratorScreen(navController, userData)
        }
        composable("wordDetails/{content}") { backStackEntry ->
            val content = backStackEntry.arguments?.getString("content")
            content?.let {
                TextFileItemUI(it)
            }
        }
    }
}