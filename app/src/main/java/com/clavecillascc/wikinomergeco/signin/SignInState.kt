package com.clavecillascc.wikinomergeco.signin

data class SignInState(
    val isSignInSuccessful: Boolean = true,
    val signInError: String? = null
)