package com.felisreader.user.presentation.signin

sealed class SignInStep {
    object Notice: SignInStep()
    data class Form(val error: Boolean): SignInStep()
    data class Processing(val success: Boolean): SignInStep()
}