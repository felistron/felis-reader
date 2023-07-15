package com.felisreader.manga.domain.model.api

import com.felisreader.manga.domain.model.MangaEntity
import com.google.gson.annotations.SerializedName

data class MangaResponse(
    val result: String,
    val response: String,
    val data: MangaEntity
)
