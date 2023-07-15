package com.felisreader.manga.presentation.manga_list

import com.felisreader.core.domain.MangaListRequest

sealed class MangaListEvent {
    object LoadMore: MangaListEvent()
    object ToggleFilter: MangaListEvent()
    data class ApplyFilter(val query: MangaListRequest): MangaListEvent()
}