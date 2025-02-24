package com.felisreader.manga.presentation.manga_search

import com.felisreader.manga.domain.model.api.MangaListQuery

sealed class SearchEvent {
    object LoadMore: SearchEvent()
    object ToggleFilter: SearchEvent()
    data class SearchBarActive(val active: Boolean): SearchEvent()
    data class ApplyFilter(val query: MangaListQuery): SearchEvent()
    data class DeleteHistoryItem(val item: String): SearchEvent()
    data class AddHistoryItem(val content: String, val timestamp: Long): SearchEvent()
    data class OnSearchTextChange(val text: String): SearchEvent()
    data class OnSearch(val title: String): SearchEvent()
    data class LoadMangaList(val title: String?, val tag: String?, val callback: suspend () -> Unit = {}): SearchEvent()
}
