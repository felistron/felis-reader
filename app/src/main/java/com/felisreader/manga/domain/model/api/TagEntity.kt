package com.felisreader.manga.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import java.util.UUID

data class TagEntity(
    val id: UUID,
    val type: EntityType,
    val attributes: TagAttributes,
)
