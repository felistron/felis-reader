package com.felisreader.chapter.presentation.chapter_list

sealed class ChapterListEvent {
    data class FeedChapters(val id: String): ChapterListEvent()
}
