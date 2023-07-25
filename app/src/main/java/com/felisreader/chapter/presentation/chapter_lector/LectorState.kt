package com.felisreader.chapter.presentation.chapter_lector

import com.felisreader.chapter.domain.model.AggregateChapter
import com.felisreader.chapter.domain.model.Chapter

data class LectorState(
    val images: List<String> = emptyList(),
    val chapter: Chapter? = null,
    val nextChapter: AggregateChapter? = null,
    val prevChapter: AggregateChapter? = null,
    val chapterIdList: List<AggregateChapter> = emptyList(),
    val loading: Boolean = true,
)
