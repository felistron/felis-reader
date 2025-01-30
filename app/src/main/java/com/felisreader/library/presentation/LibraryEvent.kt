package com.felisreader.library.presentation

sealed class LibraryEvent {
    data class SignInDialogVisible(val visible: Boolean): LibraryEvent()
}