package com.felisreader.user.domain.model

data class RefreshTokenQuery(
    val refreshToken: String,
    val clientId: String,
    val clientSecret: String,
)
