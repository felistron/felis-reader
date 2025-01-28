package com.felisreader.library.presentation

sealed class LibraryEvent {
    data class SignInDialogVisible(val visible: Boolean): LibraryEvent()
    data class SignIn(
        val username: String,
        val password: String,
        val clientId: String,
        val clientSecret: String,
        val remember: Boolean,
    ): LibraryEvent()
}