package com.felisreader.user.domain.model.api

data class UserRatingResponse(
    val result: String,
    val ratings: Map<String, Rating>
)
