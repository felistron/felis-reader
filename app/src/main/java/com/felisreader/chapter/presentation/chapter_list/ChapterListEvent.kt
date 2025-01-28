package com.felisreader.chapter.presentation.chapter_list

sealed class ChapterListEvent {
    data class FeedChapters(
        val mangaId: String,
        val callback: suspend () -> Unit = {},
    ): ChapterListEvent()
    object LoadMore: ChapterListEvent()
    object ToggleOrder: ChapterListEvent()
}
