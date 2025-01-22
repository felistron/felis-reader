package com.felisreader.library.presentation.manga_history

sealed class MangaHistoryEvent {
    object LoadHistory: MangaHistoryEvent()
    object LoadMore: MangaHistoryEvent()
    class DeleteHistoryItem(val mangaId: String): MangaHistoryEvent()
}