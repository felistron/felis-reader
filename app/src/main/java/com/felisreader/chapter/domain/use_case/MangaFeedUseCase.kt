package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.chapter.domain.model.api.ChapterListResponse
import com.felisreader.chapter.domain.repository.ChapterRepository

class MangaFeedUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(query: FeedQuery): ChapterListResponse? {
        return chapterRepository.getMangaFeed(query)
    }
}