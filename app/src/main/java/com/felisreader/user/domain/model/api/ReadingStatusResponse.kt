package com.felisreader.user.domain.model.api

data class ReadingStatusResponse(
    val result: String,
    val statuses: Map<String, ReadingStatus>
)
