package com.clavecillascc.wikinomergeco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.clavecillascc.wikinomergeco.ui.theme.WikinoMergeCoTheme
import com.clavecillascc.wikinomergeco.ui.theme.appDarkBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
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
                    val systemUiController = rememberSystemUiController()
                    SideEffect {
                        systemUiController.setStatusBarColor(appDarkBlue)
                    }
                    HomeScreen()

                }
            }
        }
    }
}
