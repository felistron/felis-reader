package com.felisreader.author.data.repository

import com.felisreader.author.data.source.remote.AuthorService
import com.felisreader.author.domain.model.api.AuthorResponse
import com.felisreader.author.domain.repository.AuthorRepository
import com.felisreader.core.domain.model.api.EntityType
import retrofit2.Response

class AuthorRepositoryImp(
    private val authorService: AuthorService
) : AuthorRepository {
    override suspend fun getAuthorById(id: String, includes: List<EntityType>?): AuthorResponse? {
        val response: Response<AuthorResponse> = authorService.getAuthorById(
            id = id,
            includes = includes?.map { it.apiName }
        )
        return response.body()
    }
}