package com.clavecillascc.wikinomergeco.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.TranslateScreen.route
    ) {
        composable(BottomBarScreen.TranslateScreen.route) {
            TranslateScreen()
        }
        composable(BottomBarScreen.LibraryScreen.route) {
            LibraryScreen()
        }
        composable(BottomBarScreen.FavoritesScreen.route) {
            FavoritesScreen()
        }
        composable(BottomBarScreen.CollaboratorScreen.route) {
            CollaborationScreen()
        }
    }
}

