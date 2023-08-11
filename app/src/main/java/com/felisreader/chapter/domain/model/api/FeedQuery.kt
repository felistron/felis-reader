package com.felisreader.chapter.domain.model.api

import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.core.domain.model.api.EntityType
import java.util.UUID

data class FeedQuery(
    val id: UUID,
    val limit: Int? = null,
    val offset: Int? = null,
    val translatedLanguage: List<String>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val contentRating: List<ContentRating>? = null,
    val excludedGroups: List<UUID>? = null,
    val excludedUploaders: List<UUID>? = null,
    val includeFutureUpdates: Boolean? = null,
    val createdAtSince: String? = null,
    val updatedAtSince: String? = null,
    val publishAtSince: String? = null,
    val order: List<ChapterOrder>? = null,
    val includes: List<EntityType>? = null,
    val includeEmptyPages: Boolean? = null,
    val includeFuturePublishAt: Boolean? = null,
    val includeExternalUrl: Boolean? = null
)
