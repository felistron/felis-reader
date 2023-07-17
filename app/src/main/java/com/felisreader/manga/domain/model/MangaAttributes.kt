package com.felisreader.manga.domain.model

import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.domain.model.PublicationDemographic
import com.felisreader.core.domain.model.State
import com.felisreader.core.domain.model.Status
import java.util.UUID

data class MangaAttributes(
    val title: Map<String, String>,
    val altTitles: List<Map<String, String>>,
    val description: Map<String, String>,
    val isLocked: Boolean,
    val links: Map<String, String>,
    val originalLanguage: String,
    val lastVolume: String?,
    val lastChapter: String?,
    val publicationDemographic: PublicationDemographic?,
    val status: Status,
    val year: Int?,
    val contentRating: ContentRating,
    val chapterNumbersResetOnNewVolume: Boolean,
    val availableTranslatedLanguages: List<String>, // TODO: Make it an enum
    val latestUploadedChapter: UUID,
    val tags: List<TagEntity>,
    val state: State,
    val version: Int,
    val createdAt: String,
    val updatedAt: String
)
