package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.AtHomeResponse
import com.felisreader.chapter.domain.repository.ChapterRepository

class FeedChapterUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(chapterId: String): AtHomeResponse? {
        return chapterRepository.getChapterFeed(chapterId)
    }
}