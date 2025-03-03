package com.felisreader.manga.presentation.manga_info

import androidx.compose.foundation.ScrollState
import com.felisreader.manga.domain.model.Manga
import com.felisreader.user.domain.model.api.ReadingStatus

data class MangaState(
    val manga: Manga? = null,
    val loading: Boolean = true,
    val isDescriptionCollapsed: Boolean = true,
    val scrollState: ScrollState = ScrollState(initial = 0),
    val ratingDialogVisible: Boolean = false,
    val loggedIn: Boolean = false,
    val signInDialogVisible: Boolean = false,
    val userRating: Int = 0,
    val readingStatus: ReadingStatus? = null,
    val readingStatusDialogVisible: Boolean = false,
)