package com.felisreader.chapter.presentation.chapter_lector

import com.felisreader.chapter.domain.model.api.AtHomeReportBody

sealed class LectorEvent {
    data class LoadLector(val chapterId: String): LectorEvent()
    data class Report(val body: AtHomeReportBody): LectorEvent()
}
