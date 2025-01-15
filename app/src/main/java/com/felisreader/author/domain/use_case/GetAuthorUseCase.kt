package com.felisreader.author.domain.use_case

import com.felisreader.author.domain.model.api.AuthorEntity
import com.felisreader.author.domain.repository.AuthorRepository
import com.felisreader.core.domain.model.api.EntityType

class GetAuthorUseCase(
    private val authorRepository: AuthorRepository
) {
    suspend operator fun invoke(id: String, includes: List<EntityType>): AuthorEntity? {
        val response = authorRepository.getAuthorById(id, includes)
        return response?.data
    }
}