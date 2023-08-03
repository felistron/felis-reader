package com.felisreader.manga.domain.use_case

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.MangaResponse
import com.felisreader.manga.domain.repository.MangaRepository

class GetMangaUseCase(
    private val mangaRepository: MangaRepository
) {
    suspend operator fun invoke(
        id: String,
        includes: List<EntityType>?
    ): Manga? {
        val response: MangaResponse? = mangaRepository.getMangaById(id, includes)

        return if (response != null) {
            MangaUtil.mangaEntityToManga(response.data)
        } else {
            null
        }
    }
}