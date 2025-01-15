package com.felisreader.author.data.source.remote

import com.felisreader.author.domain.model.api.AuthorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorService {
    @GET("author/{id}")
    suspend fun getAuthorById(
        @Path("id") id: String,
        @Query("includes[]", encoded = true) includes: List<String>?
    ): Response<AuthorResponse>
}