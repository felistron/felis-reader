package com.felisreader.manga.domain.use_case

import com.felisreader.manga.domain.model.api.TagEntity
import com.felisreader.manga.domain.repository.MangaRepository

class GetMangaTagsUseCase(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(): List<TagEntity> {
        return repository.getMangaTags()?.data ?: emptyList()
    }
}