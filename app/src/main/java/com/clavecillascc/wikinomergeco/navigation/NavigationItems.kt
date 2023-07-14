package com.clavecillascc.wikinomergeco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.loginAndSignUp.LoginAndSignUpViewModel
import com.clavecillascc.wikinomergeco.loginAndSignUp.LoginScreen
import com.clavecillascc.wikinomergeco.loginAndSignUp.SignUpScreen
import com.clavecillascc.wikinomergeco.mainScreen.MainScreen
import com.clavecillascc.wikinomergeco.mainScreen.MainScreenViewModel

sealed class NavigationItems(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItems("home", R.drawable.home, "Home")
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
    loginAndSignUpViewModel: LoginAndSignUpViewModel,
    mainScreenViewModel: MainScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NestedRoutes.Main.name
    ) {
        authGraph(navController, loginAndSignUpViewModel)
        homeGraph(
            navController = navController,
            mainScreenViewModel
        )
    }
}
fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginAndSignUpViewModel: LoginAndSignUpViewModel
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
                loginAndSignUpViewModel = loginAndSignUpViewModel

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
                loginAndSignUpViewModel = loginAndSignUpViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel
) {
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ) {
        composable(HomeRoutes.Home.name) {
            MainScreen(
                mainScreenViewModel = mainScreenViewModel
            ) {
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