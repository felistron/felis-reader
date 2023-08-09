package com.felisreader.manga.domain.model.api

import com.felisreader.manga.domain.model.Manga

data class MangaList(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val data: List<Manga>
)
