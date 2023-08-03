package com.felisreader.chapter.domain.model.api

import com.felisreader.chapter.domain.model.Chapter
import com.felisreader.core.domain.model.api.ApiError

data class FeedResponse(
    val result: String,
    val response: String,
    val data: List<Chapter>,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val errors: List<ApiError>?
)
