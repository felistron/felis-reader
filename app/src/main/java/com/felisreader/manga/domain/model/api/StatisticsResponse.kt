package com.felisreader.manga.domain.model.api

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, Statistics>,
)
