package com.felisreader.chapter.domain.model

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.Relationship
import java.util.UUID

data class Chapter(
    val id: UUID,
    val type: EntityType,
    val attributes: ChapterAttributes,
    val relationships: List<Relationship>
)
