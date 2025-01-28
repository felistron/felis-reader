package com.felisreader.manga.presentation.manga_home

sealed class HomeEvent {
    data class LoadManga(val callback: suspend () -> Unit = {}): HomeEvent()
    data class CloseWelcomeDialog(val showAgain: Boolean): HomeEvent()
}