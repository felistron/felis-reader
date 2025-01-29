package com.felisreader.user.domain.model.api

data class UserResponse(
    val result: String,
    val response: String,
    val data: UserEntity
)
