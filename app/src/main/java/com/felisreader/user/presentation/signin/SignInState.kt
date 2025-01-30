package com.felisreader.user.presentation.signin

data class SignInState(
    val username: String = "",
    val password: String = "",
    val clientId: String = "",
    val clientSecret: String = "",
    val nextDialog: Boolean = false,
    val noticeChecked: Boolean = false,
    val currentStep: SignInStep = SignInStep.Notice,
    val showCredentialsError: Boolean = false,
)
