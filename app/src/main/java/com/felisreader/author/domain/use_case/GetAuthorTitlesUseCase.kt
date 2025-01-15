package com.felisreader.author.domain.use_case

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.repository.MangaRepository

class GetAuthorTitlesUseCase(
    private val mangaRepository: MangaRepository
) {
    suspend operator fun invoke(authorId: String, limit: Int): MangaList? {
        val response = mangaRepository.getMangaList(
            MangaListQuery(
                limit = limit,
                authors = listOf(authorId),
                includes = listOf(EntityType.COVER_ART, EntityType.AUTHOR)
            )
        ) ?: return null

        return MangaList(
            limit = response.limit,
            offset = response.offset,
            total = response.total,
            data = response.data.map {
                MangaUtil.mangaEntityToManga(it)
            }
        )
    }
}