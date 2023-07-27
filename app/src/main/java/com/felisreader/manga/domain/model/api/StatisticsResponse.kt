package com.felisreader.manga.domain.model.api

import com.felisreader.core.domain.model.api.ApiError
import com.felisreader.manga.domain.model.Statistics

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, Statistics>,
    val errors: List<ApiError>?
)
