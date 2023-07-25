package com.felisreader.chapter.domain.model

data class AggregateVolume(
    val volume: String,
    val count: Int,
    val chapters: Map<String, AggregateChapter>
)
