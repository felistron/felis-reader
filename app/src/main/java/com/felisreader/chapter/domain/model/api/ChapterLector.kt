package com.felisreader.chapter.domain.model.api

data class ChapterLector(
    val hash: String,
    val data: List<String>,
    val dataSaver: List<String>
)
