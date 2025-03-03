package com.felisreader.manga.presentation.manga_info

import com.felisreader.user.domain.model.api.ReadingStatus

sealed class MangaEvent {
    object ToggleDescription: MangaEvent()
    data class LoadManga(val mangaId: String): MangaEvent()
    data class SetRatingDialogVisible(val visible: Boolean): MangaEvent()
    data class SetSignInDialogVisible(val visible: Boolean): MangaEvent()
    object SignInSuccess: MangaEvent()
    data class SubmitRating(val rating: Int): MangaEvent()
    data class SetReadingStatusDialogVisible(val visible: Boolean): MangaEvent()
    data class SubmitReadingStatus(val status: ReadingStatus?): MangaEvent()
}