package com.felisreader.manga.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.data.source.remote.MangaService
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.model.api.TagResponse
import com.felisreader.manga.domain.repository.MangaRepository
import retrofit2.Response
import java.time.format.DateTimeFormatter


class MangaRepositoryImp(
    private val mangaService: MangaService
) : MangaRepository {
    override suspend fun getMangaById(id: String, includes: List<EntityType>?): MangaResponse? {
        val response: Response<MangaResponse> = mangaService.getMangaById(
            id = id,
            includes = includes?.map { it.apiName }
        )
        return response.body()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMangaList(req: MangaListQuery): MangaListResponse? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

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
            status = req.status?.map { it.apiName },
            originalLanguage = req.originalLanguage,
            excludedOriginalLanguage = req.excludedOriginalLanguage,
            availableTranslatedLanguage = req.availableTranslatedLanguage,
            publicationDemographic = req.publicationDemographic?.map { it.apiName },
            ids = req.ids,
            contentRating = req.contentRating?.map { it.apiName },
            createdAtSince = if (req.createdAtSince == null) { null } else { formatter.format(req.createdAtSince) },
            updatedAtSince = if (req.updatedAtSince == null) { null } else { formatter.format(req.updatedAtSince) },
            order = req.order?.associate { "order[${it.name}]" to it.orderType.name } ?: emptyMap(),
            includes = req.includes?.map { it.apiName },
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

    override suspend fun getMangaTags(): TagResponse? {
        val response: Response<TagResponse> = mangaService.getMangaTags()
        return response.body()
    }
}
