package com.felisreader.user.domain.model.api

data class ReadingStatusListResponse(
    val result: String,
    val statuses: Map<String, ReadingStatus>
)
