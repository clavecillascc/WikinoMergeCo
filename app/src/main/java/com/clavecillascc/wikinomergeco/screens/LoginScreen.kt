package com.clavecillascc.wikinomergeco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.clavecillascc.wikinomergeco.R
import com.clavecillascc.wikinomergeco.data.login.LoginUIEvent
import com.clavecillascc.wikinomergeco.navigation.Screen
import com.clavecillascc.wikinomergeco.navigation.SystemBackButtonHandler
import com.clavecillascc.wikinomergeco.navigation.WikinoMergeCoRouter
import com.clavecillascc.wikinomergeco.data.login.LoginViewModel
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.message),
                onTextChanged = {
                    loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                },
                errorStatus = loginViewModel.loginUIState.value.emailError
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.lock),
                onTextChanged = {
                    {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                }
            )
            UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))
            ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = { /*TODO*/ })
            DividerTextComponent()
            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                WikinoMergeCoRouter.navigateTo(Screen.SignUpScreen)
            })
        }
    }
    SystemBackButtonHandler {
        WikinoMergeCoRouter.navigateTo(Screen.SignUpScreen)
    }
}
