package com.felisreader.chapter.domain.repository

import com.felisreader.chapter.domain.model.api.Aggregate
import com.felisreader.chapter.domain.model.api.AtHomeResponse
import com.felisreader.chapter.domain.model.api.*
import com.felisreader.user.domain.model.ApiResult

interface ChapterRepository {
    suspend fun getMangaFeed(query: FeedQuery): ChapterListResponse?
    suspend fun getChapterFeed(chapterId: String): AtHomeResponse?
    suspend fun getAggregate(query: AggregateQuery): Aggregate?
    suspend fun getChapter(query: ChapterQuery): ChapterResponse?
    suspend fun postReport(body: AtHomeReportBody)
    suspend fun getChapterList(query: ChapterListQuery): ApiResult<ChapterListResponse>
}