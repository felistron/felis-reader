package com.felisreader.user.domain.model.api

data class UserAttributes(
    val username: String,
    val roles: List<UserRole>,
    val version: Int
)
