package com.clavecillascc.wikinomergeco.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.screens.CebuanoScreen
import com.clavecillascc.wikinomergeco.screens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.screens.HomeScreen
import com.clavecillascc.wikinomergeco.screens.IlocanoScreen
import com.clavecillascc.wikinomergeco.screens.LibraryScreen
import com.clavecillascc.wikinomergeco.screens.TagalogScreen
import com.clavecillascc.wikinomergeco.screens.TranslateScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen()
        }
        composable(route = "translate") {
            TranslateScreen()
        }
        composable(route = "library") {
            LibraryScreen(navController)
        }
        composable(route = "collaborator") {
            CollaboratorScreen()
        }
        composable(route = "tagalogScreen") {
            TagalogScreen()
        }

        composable(route = "cebuanoScreen") {
            CebuanoScreen()
        }

        composable(route = "ilocanoScreen") {
            IlocanoScreen()
        }
    }
}