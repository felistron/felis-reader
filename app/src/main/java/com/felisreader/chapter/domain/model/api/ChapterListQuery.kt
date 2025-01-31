package com.felisreader.chapter.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.domain.model.api.ContentRating
import java.time.LocalDateTime

data class ChapterListQuery(
    val limit: Int? = null,
    val offset: Int? = null,
    val ids: List<String>? = null,
    val title: String? = null,
    val groups: List<String>? = null,
    val uploader: List<String>? = null,
    val manga: String? = null,
    val volume: List<String>? = null,
    val chapter: List<String>? = null,
    val translatedLanguage: List<String>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val contentRating: List<ContentRating>? = null,
    val excludedGroups: List<String>? = null,
    val excludedUploaders: List<String>? = null,
    val includeFutureUpdates: Boolean? = null,
    val includeEmptyPages: Boolean? = null,
    val includeFuturePublishAt: Boolean? = null,
    val includeExternalUrl: Boolean? = null,
    val createdAtSince: LocalDateTime? = null,
    val updatedAtSince: LocalDateTime? = null,
    val publishAtSince: LocalDateTime? = null,
    val order: List<ChapterOrder>? = null,
    val includes: List<EntityType>? = null,
)