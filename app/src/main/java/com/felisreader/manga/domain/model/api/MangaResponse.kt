package com.felisreader.manga.domain.model.api

data class MangaResponse(
    val result: String,
    val response: String,
    val data: MangaEntity
)
