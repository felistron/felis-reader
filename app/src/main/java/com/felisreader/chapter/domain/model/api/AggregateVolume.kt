package com.felisreader.chapter.domain.model.api

data class AggregateVolume(
    val volume: String,
    val count: Int,
    val chapters: Map<String, AggregateChapter>
)
