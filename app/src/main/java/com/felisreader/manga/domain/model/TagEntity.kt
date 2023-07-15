package com.felisreader.manga.domain.model

import com.felisreader.core.domain.EntityType
import com.felisreader.core.domain.Relationship
import java.util.UUID

data class TagEntity(
    val id: UUID,
    val type: EntityType,
    val attributes: TagAttributes,
    val relationships: List<Relationship>
)
