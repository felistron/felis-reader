package com.felisreader.chapter.domain.model.api

data class Aggregate(
    val result: String,
    val volumes: Map<String, AggregateVolume>
)