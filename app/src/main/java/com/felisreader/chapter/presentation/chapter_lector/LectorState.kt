package com.felisreader.chapter.presentation.chapter_lector

import androidx.compose.foundation.lazy.LazyListState
import com.felisreader.chapter.domain.model.api.AggregateChapter
import com.felisreader.chapter.domain.model.api.Chapter

data class LectorState(
    val images: List<String> = emptyList(),
    val chapter: Chapter? = null,
    val nextChapter: AggregateChapter? = null,
    val prevChapter: AggregateChapter? = null,
    val chapterIdList: List<AggregateChapter> = emptyList(),
    val loading: Boolean = true,
    val lazyListState: LazyListState = LazyListState()
)
