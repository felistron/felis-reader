package com.felisreader.chapter.domain.model

data class AggregateChapter(
    val chapter: String,
    val id: String,
    val others: List<String>,
    val count: Int
)
