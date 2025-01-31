package com.felisreader.library.presentation.manga_history

import com.felisreader.manga.domain.model.Manga

data class MangaHistoryState(
    val history: List<Manga>? = null,
    val offset: Int = 0,
    val canLoadMore: Boolean = true,
)
