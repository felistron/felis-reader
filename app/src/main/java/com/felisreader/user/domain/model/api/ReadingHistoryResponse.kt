package com.felisreader.user.domain.model.api

data class ReadingHistoryResponse(
    val result: String,
    val ratings: List<ReadingHistory>
)
