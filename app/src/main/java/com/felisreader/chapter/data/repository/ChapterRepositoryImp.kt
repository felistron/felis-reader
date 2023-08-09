package com.felisreader.chapter.data.repository

import com.felisreader.chapter.data.source.remote.ChapterService
import com.felisreader.chapter.domain.model.api.Aggregate
import com.felisreader.chapter.domain.model.api.AtHomeResponse
import com.felisreader.chapter.domain.model.api.*
import com.felisreader.chapter.domain.repository.ChapterRepository
import retrofit2.Response

class ChapterRepositoryImp(
    private val chapterService: ChapterService
) : ChapterRepository {
    override suspend fun getMangaFeed(query: FeedQuery): FeedResponse? {
        val response: Response<FeedResponse> = chapterService.getMangaFeed(
            id = query.id.toString(),
            offset = query.offset,
            limit = query.limit,
            translatedLanguage = query.translatedLanguage,
            originalLanguage = query.originalLanguage,
            excludedOriginalLanguage = query.excludedOriginalLanguage,
            contentRating = query.contentRating?.map { it.apiName },
            excludedGroups = query.excludedGroups?.map { it.toString() },
            excludedUploaders = query.excludedUploaders?.map { it.toString() },
            includeFutureUpdates = if (query.includeFutureUpdates == true) 1 else 0,
            createdAtSince = query.createdAtSince,
            updatedAtSince = query.updatedAtSince,
            publishAtSince = query.publishAtSince,
            order = query.order?.associate { "order[${it.name}]" to it.orderType.name } ?: emptyMap(),
            includes = query.includes?.map { it.apiName },
            includeEmptyPages = if (query.includeEmptyPages == true) 1 else 0,
            includeFuturePublishAt = if (query.includeFuturePublishAt == true) 1 else 0,
            includeExternalUrl = if (query.includeExternalUrl == true) 1 else 0,
        )

        return response.body()
    }

    override suspend fun getChapterFeed(chapterId: String): AtHomeResponse? {
        val response: Response<AtHomeResponse> = chapterService.getChapterFeed(chapterId)
        return response.body()
    }

    override suspend fun getAggregate(query: AggregateQuery): Aggregate? {
        val response: Response<Aggregate> = chapterService.getAggregate(
            id = query.mangaId,
            translatedLanguage = query.translatedLanguage,
            groups = query.groups
        )
        return response.body()
    }

    override suspend fun getChapter(query: ChapterQuery): ChapterResponse? {
        val response: Response<ChapterResponse> = chapterService.getChapter(
            chapterId = query.chapterId,
            includes = query.includes?.map { it.apiName }
        )
        return response.body()
    }

    override suspend fun postReport(body: AtHomeReportBody) {
        chapterService.postReport(body)
    }
}
