package com.felisreader.core.domain

import java.util.*

data class Relationship(
    val id: UUID,
    val type: EntityType,
    val related: MangaRelated?,
    // TODO: Add Reference expansion https://api.mangadex.org/docs/reference-expansion/
    val attributes: Map<Any, Any>
) {
    companion object {
        fun queryRelationship(relationships: List<Relationship>, type: EntityType): Relationship? {
            for (relationship in relationships) {
                if (relationship.type == type) return relationship
            }
            return null
        }
    }
}
