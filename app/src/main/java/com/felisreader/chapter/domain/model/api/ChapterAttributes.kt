package com.felisreader.chapter.domain.model.api

import java.util.UUID

data class ChapterAttributes(
    val title: String?,
    val volume: String?,
    val chapter: String?,
    val pages: Int,
    val translatedLanguage: String?,
    val uploader: UUID,
    val externalUrl: String?,
    val version: Int,
    val createdAt: String,
    val updatedAt: String,
    val publishAt: String,
    val readableAt: String
)
