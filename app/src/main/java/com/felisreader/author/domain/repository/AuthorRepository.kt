package com.felisreader.author.domain.repository

import com.felisreader.author.domain.model.api.AuthorResponse
import com.felisreader.core.domain.model.api.EntityType

interface AuthorRepository {
    suspend fun getAuthorById(id: String, includes: List<EntityType>?): AuthorResponse?
}