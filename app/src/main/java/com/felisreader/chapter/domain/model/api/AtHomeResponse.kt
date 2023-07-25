package com.felisreader.chapter.domain.model.api

import com.felisreader.core.domain.model.api.ApiError

data class AtHomeResponse(
    val result: String,
    val baseUrl: String,
    val chapter: ChapterLector,
    val errors: List<ApiError>?
)
