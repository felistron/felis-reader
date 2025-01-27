package com.felisreader.library.presentation.manga_history

sealed class MangaHistoryEvent {
    data class LoadHistory(val callback: suspend () -> Unit = {}): MangaHistoryEvent()
    object LoadMore: MangaHistoryEvent()
    class DeleteHistoryItem(val mangaId: String): MangaHistoryEvent()
}