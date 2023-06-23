package com.clavecillascc.wikinomergeco

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.clavecillascc.wikinomergeco.screens.BottomNavigationBar
import com.clavecillascc.wikinomergeco.ui.theme.Navigation
import com.clavecillascc.wikinomergeco.ui.theme.WikinoMergeCoTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            WikinoMergeCoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(bottomBar = { BottomNavigationBar(
                        items = listOf(
                            BottomMenuContent(title = "Home", route = "home", iconId = R.drawable.translate),
                            BottomMenuContent(title = "Translate", route = "translate", iconId = R.drawable.translate),
                            BottomMenuContent(title = "Dictionary", route = "dictionary", R.drawable.library),
                            BottomMenuContent(title = "Collaborator", route = "collaborator", R.drawable.collaboration),
                        ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )}) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}