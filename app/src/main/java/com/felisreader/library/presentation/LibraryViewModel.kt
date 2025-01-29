package com.felisreader.library.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.cipher.SecurePreferencesManager
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val securePrefsManager: SecurePreferencesManager,
): ViewModel() {
    private val _state: MutableState<LibraryState> = mutableStateOf(LibraryState())
    val state: State<LibraryState> = _state

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.SignInDialogVisible -> signInDialogVisible(event.visible)
            is LibraryEvent.SignIn -> signIn(event.username, event.password, event.clientId, event.clientSecret, event.remember)
        }
    }

    private fun signIn(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        remember: Boolean
    ) {
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()

            val response = userRepository.getAccessToken(AccessTokenQuery(
                username = username,
                password = password,
                clientId = clientId,
                clientSecret = clientSecret,
            ))

            when (response) {
                is ApiResult.Success -> {
                    securePrefsManager.saveAccessToken(response.body, timestamp, clientId, clientSecret)
                    // TODO: Give feedback on success
                }
                is ApiResult.Failure -> {
                    // TODO: Give feedback about the error
                }
            }

            signInDialogVisible(false)
        }
    }

    private fun signInDialogVisible(visible: Boolean) {
        _state.value = _state.value.copy(
            signInDialogVisible = visible
        )
    }
}