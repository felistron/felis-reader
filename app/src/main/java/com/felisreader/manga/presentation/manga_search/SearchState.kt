package com.felisreader.manga.presentation.manga_search

import androidx.compose.foundation.lazy.LazyListState
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.MangaList

data class SearchState(
    val query: MangaListQuery = MangaListQuery(
        limit = LIMIT,
        offset = 0,
        includes = listOf(
            EntityType.AUTHOR,
            EntityType.COVER_ART
        ),
        hasAvailableChapters = true
    ),
    val mangaList: MangaList? = null,
    val loading: Boolean = true,
    val lazyListState: LazyListState = LazyListState(),
    val canLoadMore: Boolean = true,
    val expandedFilter: Boolean = false,
    val searchBarActive: Boolean = false,
    val welcomeDialogVisible: Boolean = false // keep false
) {
    companion object {
        const val LIMIT: Int = 20
    }
}