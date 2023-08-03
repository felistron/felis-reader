package com.felisreader.chapter.domain.model.api

import com.felisreader.chapter.domain.model.Chapter
import com.felisreader.core.domain.model.api.ApiError

data class ChapterResponse(
    val result: String,
    val response: String,
    val data: Chapter,
    val errors: List<ApiError>?
)
