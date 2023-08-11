package com.felisreader.manga.domain.model

import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.core.domain.model.api.LinkType
import com.felisreader.manga.domain.model.api.PublicationDemographic
import com.felisreader.manga.domain.model.api.Status
import com.felisreader.manga.domain.model.api.Statistics
import com.felisreader.manga.domain.model.api.TagEntity

data class Manga(
    val id: String,
    val title: String,
    val originalTitle: String,
    val coverUrl: String,
    val coverUrlSave: String,
    val description: String,
    val author: Author,
    val tags: List<TagEntity>,
    val contentRating: ContentRating,
    val demography: PublicationDemographic?,
    val year: Int?,
    val status: Status,
    val links: List<LinkType?>,
    val statistics: Statistics?
)