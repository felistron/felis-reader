package com.felisreader.user.domain.model.api

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship

data class UserEntity(
    val id: String,
    val type: EntityType,
    val attributes: UserAttributes,
    val relationships: List<Relationship>
)
