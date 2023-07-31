package com.felisreader.chapter.domain.model

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.Relationship

data class Chapter(
    val id: String,
    val type: EntityType,
    val attributes: ChapterAttributes,
    val relationships: List<Relationship>
)
