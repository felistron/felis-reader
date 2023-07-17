package com.felisreader.manga.domain.model

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.Relationship
import java.util.UUID

data class MangaEntity(
    val id: UUID,
    val type: EntityType,
    val attributes: MangaAttributes,
    val relationships: List<Relationship>
)
