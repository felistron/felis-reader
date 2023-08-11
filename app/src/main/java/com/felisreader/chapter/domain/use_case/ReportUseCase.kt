package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.AtHomeReportBody
import com.felisreader.chapter.domain.repository.ChapterRepository

class ReportUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(body: AtHomeReportBody) {
        chapterRepository.postReport(body)
    }
}