package com.clavecillascc.wikinomergeco

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.clavecillascc.wikinomergeco.loginAndSignUp.LoginViewModel
import com.clavecillascc.wikinomergeco.navigation.Navigation
import com.clavecillascc.wikinomergeco.ui.theme.WikinoMergeCoTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize Firebase
        FirebaseApp.initializeApp(this)

        installSplashScreen()
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            WikinoMergeCoTheme {
                Navigation(
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}