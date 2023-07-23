package com.felisreader.core.domain.model.api

import java.util.UUID

data class ApiError(
    val id: UUID,
    val status: Int,
    val title: String,
    val detail: String?,
    val context: String?
)
