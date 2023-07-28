package com.clavecillascc.wikinomergeco.signin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)