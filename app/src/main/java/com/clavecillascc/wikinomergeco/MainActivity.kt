package com.clavecillascc.wikinomergeco

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.clavecillascc.wikinomergeco.ui.theme.WikinoMergeCoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                            BottomMenuContent(
                                name = "Home",
                                route = "home",
                                icon = Icons.Default.Home
                            ),
                            BottomMenuContent(
                                name = "Translate",
                                route = "translate",
                                icon = Icons.Default.Home
                            ),
                            BottomMenuContent(
                                name = "Dictionary",
                                route = "dictionary",
                                icon = Icons.Default.Home
                            ),
                            BottomMenuContent(
                                name = "Collaborator",
                                route = "collaborator",
                                icon = Icons.Default.Home
                            ),
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
