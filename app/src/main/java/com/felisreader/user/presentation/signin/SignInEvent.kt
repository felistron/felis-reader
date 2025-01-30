package com.felisreader.user.presentation.signin

sealed class SignInEvent {
    data class OnUsernameChange(val username: String): SignInEvent()
    data class OnPasswordChange(val password: String): SignInEvent()
    data class OnClientIdChange(val clientId: String): SignInEvent()
    data class OnClientSecretChange(val clientSecret: String): SignInEvent()
    data class OnCheckedChange(val checked: Boolean): SignInEvent()
    data class StepChange(val nextStep: SignInStep): SignInEvent()
    data class SignIn(
        val onSuccess: suspend () -> Unit,
        val onFailure: suspend () -> Unit
    ): SignInEvent()
}