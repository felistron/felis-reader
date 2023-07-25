package com.felisreader.chapter.domain.model

data class Aggregate(
    val result: String,
    val volumes: Map<String, AggregateVolume>
)