package com.felisreader.library.presentation.reading_history

sealed class ReadingHistoryEvent {
    data class LoadHistory(val callback: suspend () -> Unit = {}): ReadingHistoryEvent()
    object LoadMore: ReadingHistoryEvent()
    data class OnDeleteChapter(val chapterId: String): ReadingHistoryEvent()
}