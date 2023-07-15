package com.felisreader.manga.domain.model.api

import com.felisreader.manga.domain.model.MangaEntity

data class MangaListResponse(
    val result: String,
    val response: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    val data: List<MangaEntity>
)
