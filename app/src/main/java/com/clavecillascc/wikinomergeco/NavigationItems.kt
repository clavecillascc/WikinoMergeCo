package com.clavecillascc.wikinomergeco

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.clavecillascc.wikinomergeco.login.LoginScreen
import com.clavecillascc.wikinomergeco.login.LoginViewModel
import com.clavecillascc.wikinomergeco.login.SignUpScreen

sealed class NavigationItems(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItems("home",R.drawable.home , "Home")
    object Translate : NavigationItems("translate", R.drawable.translate, "Translate")
    object Library : NavigationItems("library", R.drawable.library, "Library")
    object Collaborator : NavigationItems("collaborator", R.drawable.collaboration, "Collaborator")

}

enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home
}

enum class NestedRoutes {
    Main,
    Login
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NestedRoutes.Main.name
    ) {
        authGraph(navController, loginViewModel)
        homeGraph(
            navController = navController
        )
    }
}
fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ) {
        composable(HomeRoutes.Home.name) {
            MainScreen() {
                navController.navigate(NestedRoutes.Login.name) {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }
}