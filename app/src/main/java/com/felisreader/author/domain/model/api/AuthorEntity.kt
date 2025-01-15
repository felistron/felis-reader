package com.felisreader.author.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship

data class AuthorEntity(
    val id: String,
    val type: EntityType,
    val attributes: AuthorAttributes,
    val relationships: List<Relationship>
)
