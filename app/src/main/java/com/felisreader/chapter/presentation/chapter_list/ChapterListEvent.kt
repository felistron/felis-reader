package com.felisreader.chapter.presentation.chapter_list

import com.felisreader.core.domain.model.OrderType

sealed class ChapterListEvent {
    data class FeedChapters(val mangaId: String, val order: OrderType = OrderType.Descending): ChapterListEvent()
    object LoadMore: ChapterListEvent()
    object ToggleOrder: ChapterListEvent()
}
