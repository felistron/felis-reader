package com.felisreader.library.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(): ViewModel() {
    private val _state: MutableState<LibraryState> = mutableStateOf(LibraryState())
    val state: State<LibraryState> = _state

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.SignInDialogVisible -> signInDialogVisible(event.visible)
        }
    }

    private fun signInDialogVisible(visible: Boolean) {
        _state.value = _state.value.copy(
            signInDialogVisible = visible
        )
    }
}