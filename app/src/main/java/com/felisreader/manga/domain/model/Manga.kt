package com.felisreader.manga.domain.model

import com.felisreader.core.domain.ContentRating
import com.felisreader.core.domain.Status

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
    val year: Int?,
    val status: Status
)