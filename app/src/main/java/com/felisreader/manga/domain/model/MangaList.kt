package com.felisreader.manga.domain.model

data class MangaList(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val data: List<Manga>
)
