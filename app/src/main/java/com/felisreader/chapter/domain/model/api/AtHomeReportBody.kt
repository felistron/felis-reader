package com.felisreader.chapter.domain.model.api

data class AtHomeReportBody(
    val url: String,
    val success: Boolean,
    val cached: Boolean,
    val bytes: Int,
    val duration: Int
)
