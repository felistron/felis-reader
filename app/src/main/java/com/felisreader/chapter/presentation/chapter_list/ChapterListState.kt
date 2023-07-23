package com.felisreader.chapter.presentation.chapter_list

import com.felisreader.chapter.domain.model.Chapter

data class ChapterListState(
    val chapterList: Map<String, Map<String, List<Chapter>>> = emptyMap(),
    val loading: Boolean = true
)
