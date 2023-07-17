package com.felisreader.manga.domain.repository

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.MangaListQuery
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse

interface MangaRepository {
    suspend fun getMangaById(id: String, includes: List<EntityType>?): MangaResponse?
    suspend fun getMangaList(req: MangaListQuery): MangaListResponse?
}
