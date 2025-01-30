package com.felisreader.user.domain.model

data class AccessTokenQuery(
    val username: String,
    val password: String,
    val clientId: String,
    val clientSecret: String,
)
