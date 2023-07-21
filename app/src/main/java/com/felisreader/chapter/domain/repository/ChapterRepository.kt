package com.felisreader.chapter.domain.repository

import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.chapter.domain.model.api.FeedResponse

interface ChapterRepository {
    suspend fun getMangaFeed(query: FeedQuery): FeedResponse?
}