package com.felisreader.library.presentation.reading_history

import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.manga.domain.model.Manga

data class ReadingHistoryState(
    val history: List<Pair<Manga, Chapter>>? = null,
    val offset: Int = 0,
    val canLoadMore: Boolean = true,
) {
    companion object {
        const val LIMIT = 20
    }
}
