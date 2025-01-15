package com.felisreader.author.domain.model.api

data class AuthorResponse(
    val result: String,
    val response: String,
    val data: AuthorEntity
)
