package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.ChapterQuery
import com.felisreader.chapter.domain.model.api.ChapterResponse
import com.felisreader.chapter.domain.repository.ChapterRepository

class GetChapterUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(query: ChapterQuery): ChapterResponse? {
        return chapterRepository.getChapter(query)
    }
}