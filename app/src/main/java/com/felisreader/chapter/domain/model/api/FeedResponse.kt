package com.felisreader.chapter.domain.model.api

data class FeedResponse(
    val result: String,
    val response: String,
    val data: List<Chapter>,
    val limit: Int,
    val offset: Int,
    val total: Int,
)
