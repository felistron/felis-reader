package com.felisreader.manga.presentation.manga_search

import com.felisreader.core.domain.model.MangaListQuery

sealed class SearchEvent {
    object LoadMore: SearchEvent()
    object ToggleFilter: SearchEvent()
    data class SearchBarActive(val active: Boolean): SearchEvent()
    data class ApplyFilter(val query: MangaListQuery): SearchEvent()
}