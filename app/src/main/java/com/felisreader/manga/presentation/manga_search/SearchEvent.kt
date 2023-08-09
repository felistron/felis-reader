package com.felisreader.manga.presentation.manga_search

import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.core.domain.model.SearchHistoryEntity

sealed class SearchEvent {
    object LoadMore: SearchEvent()
    object ToggleFilter: SearchEvent()
    data class SearchBarActive(val active: Boolean): SearchEvent()
    data class ApplyFilter(val query: MangaListQuery): SearchEvent()
    data class DeleteHistoryItem(val item: SearchHistoryEntity): SearchEvent()
    data class AddHistoryItem(val content: String, val timestamp: Long): SearchEvent()
    data class CloseWelcomeDialog(val showAgain: Boolean): SearchEvent()
}
