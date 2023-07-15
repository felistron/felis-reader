package com.felisreader.manga.domain.repository

import com.felisreader.core.domain.EntityType
import com.felisreader.core.domain.MangaListRequest
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse

interface MangaRepository {
    suspend fun getMangaById(id: String, includes: List<EntityType>?): MangaResponse?
    suspend fun getMangaList(req: MangaListRequest): MangaListResponse?
}
