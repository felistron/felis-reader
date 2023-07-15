package com.felisreader.manga.domain.model

data class TagAttributes(
    val name: Map<String, String>,
    val description: Map<String, String>,
    val group: TagGroup,
    val version: Int
)
