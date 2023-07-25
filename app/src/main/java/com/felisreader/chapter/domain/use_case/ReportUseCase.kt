package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.ReportBody
import com.felisreader.chapter.domain.repository.ChapterRepository

class ReportUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(body: ReportBody) {
        chapterRepository.postReport(body)
    }
}