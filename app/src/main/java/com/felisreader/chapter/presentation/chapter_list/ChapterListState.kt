package com.felisreader.chapter.presentation.chapter_list

import com.felisreader.chapter.domain.model.Chapter
import com.felisreader.chapter.domain.model.api.FeedQuery

data class ChapterListState(
    val feedQuery: FeedQuery? = null,
    val chapterList: List<Chapter> = emptyList(),
    val loading: Boolean = true,
    val canLoadMore: Boolean = true
) {
    companion object {
        const val LIMIT: Int = 100
    }
}
