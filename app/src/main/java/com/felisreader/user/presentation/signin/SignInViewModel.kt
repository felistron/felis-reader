package com.felisreader.user.presentation.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.cipher.SecurePreferencesManager
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val securePrefsManager: SecurePreferencesManager,
) : ViewModel() {
    private val _state: MutableState<SignInState> = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnUsernameChange ->
                _state.value = _state.value.copy(username = event.username)
            is SignInEvent.OnPasswordChange ->
                _state.value = _state.value.copy(password = event.password)
            is SignInEvent.OnClientIdChange ->
                _state.value = _state.value.copy(clientId = event.clientId)
            is SignInEvent.OnClientSecretChange ->
                _state.value = _state.value.copy(clientSecret = event.clientSecret)
            is SignInEvent.SignIn ->
                signIn(event.onSuccess, event.onFailure)
            is SignInEvent.OnCheckedChange ->
                _state.value = _state.value.copy(noticeChecked = event.checked)
            is SignInEvent.StepChange ->
                _state.value = _state.value.copy(currentStep = event.nextStep)
        }
    }

    private fun signIn(onSuccess: suspend () -> Unit, onFailure: suspend () -> Unit) {
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()

            val response = authRepository.createAccessToken(
                AccessTokenQuery(
                    username = _state.value.username,
                    password = _state.value.password,
                    clientId = _state.value.clientId,
                    clientSecret = _state.value.clientSecret,
                )
            )

            when (response) {
                is ApiResult.Success -> {
                    securePrefsManager.saveAccessToken(
                        accessToken = response.body,
                        timestamp = timestamp,
                        clientId = _state.value.clientId,
                        clientSecret = _state.value.clientSecret,
                    )

                    onSuccess()
                }
                is ApiResult.Failure -> onFailure()
            }
        }
    }
}