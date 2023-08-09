package com.felisreader.chapter.domain.model.api

data class AggregateChapter(
    val chapter: String,
    val id: String,
    val others: List<String>,
    val count: Int
)
