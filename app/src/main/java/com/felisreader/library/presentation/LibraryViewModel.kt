package com.felisreader.library.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {
    private val _state: MutableState<LibraryState> = mutableStateOf(LibraryState())
    val state: State<LibraryState> = _state

    init {
        viewModelScope.launch {
            val response = userRepository.getLoggedUser()
            when (response) {
                is ApiResult.Success -> _state.value = _state.value.copy(isLoggedIn = true)
                is ApiResult.Failure -> _state.value = _state.value.copy(isLoggedIn = false)
            }
        }
    }

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.SignInDialogVisible -> signInDialogVisible(event.visible)
            is LibraryEvent.SignInSuccess -> signInSuccess()
        }
    }

    private fun signInDialogVisible(visible: Boolean) {
        _state.value = _state.value.copy(
            signInDialogVisible = visible
        )
    }

    private fun signInSuccess() {
        _state.value = _state.value.copy(isLoggedIn = true)
    }
}