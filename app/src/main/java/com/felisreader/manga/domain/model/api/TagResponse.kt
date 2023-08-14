package com.felisreader.manga.domain.model.api

data class TagResponse(
    val result: String,
    val response: String,
    val data: List<TagEntity>,
    val limit: Int,
    val offset: Int,
    val total: Int
)
