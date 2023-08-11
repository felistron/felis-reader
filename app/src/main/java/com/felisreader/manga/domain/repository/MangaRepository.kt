package com.felisreader.manga.domain.repository

import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse
import com.felisreader.manga.domain.model.api.StatisticsResponse

interface MangaRepository {
    suspend fun getMangaById(id: String, includes: List<EntityType>?): MangaResponse?
    suspend fun getMangaList(req: MangaListQuery): MangaListResponse?
    suspend fun getMangaStatistics(id: String): StatisticsResponse?
    suspend fun getStatistics(ids: List<String>): StatisticsResponse?
}
