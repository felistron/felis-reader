package com.felisreader.manga.presentation.manga_list

import androidx.compose.foundation.lazy.LazyListState
import com.felisreader.core.domain.*
import com.felisreader.manga.domain.model.MangaList

data class MangaListState(
    val query: MangaListRequest = MangaListRequest(
        limit = LIMIT,
        offset = 0,
        includes = listOf(
            EntityType.AUTHOR,
            EntityType.COVER_ART
        ),
        hasAvailableChapters = true,
        contentRating = listOf(
            ContentRating.SAFE
        )
    ),
    val mangaList: MangaList? = null,
    val loading: Boolean = true,
    val lazyListState: LazyListState = LazyListState(),
    val canLoadMore: Boolean = true,
    val expandedFilter: Boolean = false
) {
    companion object {
        const val LIMIT: Int = 20
    }
}