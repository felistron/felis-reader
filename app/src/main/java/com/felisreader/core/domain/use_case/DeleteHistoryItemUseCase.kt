package com.felisreader.core.domain.use_case

import com.felisreader.core.domain.repository.HistoryRepository

class DeleteHistoryItemUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(content: String) {
        historyRepository.deleteByContent(content)
    }
}