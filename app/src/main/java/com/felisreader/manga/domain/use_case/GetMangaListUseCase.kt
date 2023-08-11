package com.felisreader.manga.domain.use_case

import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.repository.MangaRepository

class GetMangaListUseCase(
    private val mangaRepository: MangaRepository
) {
    suspend operator fun invoke(req: MangaListQuery): MangaList? {
        val mangaListResponse: MangaListResponse = mangaRepository.getMangaList(req) ?: return null

        return MangaList(
            limit = mangaListResponse.limit,
            offset = mangaListResponse.offset,
            total = mangaListResponse.total,
            data = mangaListResponse.data.map {
                MangaUtil.mangaEntityToManga(it)
            }
        )
    }
}