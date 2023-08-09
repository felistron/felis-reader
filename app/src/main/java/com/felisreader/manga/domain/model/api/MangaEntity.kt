package com.felisreader.manga.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship
import java.util.UUID

data class MangaEntity(
    val id: UUID,
    val type: EntityType,
    val attributes: MangaAttributes,
    val relationships: List<Relationship>
)
