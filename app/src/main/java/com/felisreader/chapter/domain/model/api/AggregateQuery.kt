package com.felisreader.chapter.domain.model.api

data class AggregateQuery(
    val mangaId: String,
    val translatedLanguage: List<String>? = null,
    val groups: List<String>? = null
)
