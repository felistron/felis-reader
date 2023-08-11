package com.felisreader.manga.domain.model.api

data class MangaListResponse(
    val result: String,
    val response: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val data: List<MangaEntity>
)
