package com.felisreader.chapter.domain.model.api

import com.felisreader.core.domain.model.api.EntityType

data class ChapterQuery(
    val chapterId: String,
    val includes: List<EntityType>? = null
)
