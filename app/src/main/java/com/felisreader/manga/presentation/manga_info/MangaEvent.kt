package com.felisreader.manga.presentation.manga_info

sealed class MangaEvent {
    object ToggleDescription: MangaEvent()
    data class LoadManga(val id: String): MangaEvent()
}