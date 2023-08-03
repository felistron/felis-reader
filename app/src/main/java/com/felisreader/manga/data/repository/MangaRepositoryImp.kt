package com.felisreader.manga.data.repository

import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.MangaListQuery
import com.felisreader.manga.data.source.remote.MangaService
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.repository.MangaRepository
import retrofit2.Response

class MangaRepositoryImp(
    private val mangaService: MangaService
) : MangaRepository {
    override suspend fun getMangaById(id: String, includes: List<EntityType>?): MangaResponse? {
        val response: Response<MangaResponse> = mangaService.getMangaById(
            id = id,
            includes = includes?.map { it.value }
        )
        return response.body()
    }

    override suspend fun getMangaList(req: MangaListQuery): MangaListResponse? {
        val response: Response<MangaListResponse> = mangaService.getMangaList(
            limit = req.limit,
            offset = req.offset,
            title = req.title?.replace(' ', '+'),
            authorOrArtist = req.authorOrArtist,
            authors = req.authors,
            artists = req.artist,
            year = req.year,
            includedTags = req.includedTags,
            includedTagsMode = req.includedTagsMode?.name,
            excludedTags = req.excludedTags,
            excludedTagsMode = req.excludedTagsMode?.name,
            status = req.status?.map { it.name.lowercase() },
            originalLanguage = req.originalLanguage,
            excludedOriginalLanguage = req.excludedOriginalLanguage,
            availableTranslatedLanguage = req.availableTranslatedLanguage,
            publicationDemographic = req.publicationDemographic?.map { it.name.lowercase() },
            ids = req.ids,
            contentRating = req.contentRating?.map { it.name.lowercase() },
            createdAtSince = null,
            updatedAtSince = null,
            order = req.order?.associate { "order[${it.name}]" to it.orderType.name } ?: emptyMap(),
            includes = req.includes?.map { it.value },
            hasAvailableChapters = req.hasAvailableChapters,
            group = req.group
        )

        return response.body()
    }

    override suspend fun getMangaStatistics(id: String): StatisticsResponse? {
        val response: Response<StatisticsResponse> = mangaService.getMangaStatistics(id)
        return response.body()
    }

    override suspend fun getStatistics(ids: List<String>): StatisticsResponse? {
        val response: Response<StatisticsResponse> = mangaService.getStatistics(ids)
        return response.body()
    }
}
