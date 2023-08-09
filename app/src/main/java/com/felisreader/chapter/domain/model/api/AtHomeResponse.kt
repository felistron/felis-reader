package com.felisreader.chapter.domain.model.api

data class AtHomeResponse(
    val result: String,
    val baseUrl: String,
    val chapter: ChapterLector
)
