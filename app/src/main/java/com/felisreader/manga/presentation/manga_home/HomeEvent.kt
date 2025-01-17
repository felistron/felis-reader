package com.felisreader.manga.presentation.manga_home

sealed class HomeEvent {
    object LoadPopular: HomeEvent()
    object LoadRecent: HomeEvent()
}