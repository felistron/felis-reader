package com.felisreader.manga.presentation.manga_info

sealed class MangaEvent {
    object ToggleDescription: MangaEvent()
    data class LoadManga(val mangaId: String): MangaEvent()
    data class SetRatingDialogVisible(val visible: Boolean): MangaEvent()
    data class SetSignInDialogVisible(val visible: Boolean): MangaEvent()
    object SignInSuccess: MangaEvent()
    data class SubmitRating(val rating: Int): MangaEvent()
}