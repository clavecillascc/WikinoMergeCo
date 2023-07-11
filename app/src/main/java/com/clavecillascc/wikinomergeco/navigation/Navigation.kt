package com.clavecillascc.wikinomergeco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clavecillascc.wikinomergeco.screens.CollaboratorScreen
import com.clavecillascc.wikinomergeco.screens.HomeScreen
import com.clavecillascc.wikinomergeco.screens.LibraryScreen
import com.clavecillascc.wikinomergeco.screens.LoginScreen
import com.clavecillascc.wikinomergeco.screens.SignUpScreen
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
        composable(route = "library"){
            LibraryScreen()
        }
        composable(route = "collaborator"){
            CollaboratorScreen()
        }
        composable(route = "login"){
            LoginScreen()
        }
        composable(route = "signup"){
            SignUpScreen()
        }
    }
}