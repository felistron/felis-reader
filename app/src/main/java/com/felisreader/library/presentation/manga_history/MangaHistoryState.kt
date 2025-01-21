package com.felisreader.library.presentation.manga_history

import com.felisreader.manga.domain.model.api.MangaList

data class MangaHistoryState(
    val history: MangaList? = null,
    val offset: Int = 0,
    val canLoadMore: Boolean = true,
)
