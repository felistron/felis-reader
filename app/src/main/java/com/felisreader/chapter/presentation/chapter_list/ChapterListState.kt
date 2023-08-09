package com.felisreader.chapter.presentation.chapter_list

import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.core.domain.model.OrderType

data class ChapterListState(
    val feedQuery: FeedQuery? = null,
    val chapterList: List<Chapter> = emptyList(),
    val loading: Boolean = true,
    val canLoadMore: Boolean = true,
    val order: OrderType = OrderType.Descending
) {
    companion object {
        const val LIMIT: Int = 100
    }
}
