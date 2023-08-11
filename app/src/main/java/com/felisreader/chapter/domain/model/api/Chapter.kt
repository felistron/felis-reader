package com.felisreader.chapter.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship

data class Chapter(
    val id: String,
    val type: EntityType,
    val attributes: ChapterAttributes,
    val relationships: List<Relationship>
)
