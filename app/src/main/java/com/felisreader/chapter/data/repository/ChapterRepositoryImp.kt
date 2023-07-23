package com.felisreader.chapter.data.repository

import com.felisreader.chapter.data.source.remote.ChapterService
import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.chapter.domain.model.api.FeedResponse
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
            contentRating = query.contentRating?.map { it.name.lowercase() },
            excludedGroups = query.excludedGroups?.map { it.toString() },
            excludedUploaders = query.excludedUploaders?.map { it.toString() },
            includeFutureUpdates = if (query.includeFutureUpdates == true) 1 else 0,
            createdAtSince = query.createdAtSince,
            updatedAtSince = query.updatedAtSince,
            publishAtSince = query.publishAtSince,
            order = query.order?.associate { "order[${it.name}]" to it.orderType.name } ?: emptyMap(),
            includes = query.includes?.map { it.value },
            includeEmptyPages = if (query.includeEmptyPages == true) 1 else 0,
            includeFuturePublishAt = if (query.includeFuturePublishAt == true) 1 else 0,
            includeExternalUrl = if (query.includeExternalUrl == true) 1 else 0,
        )

        return response.body()
    }
}
