package com.felisreader.core.domain.use_case

import com.felisreader.core.domain.model.SearchHistoryEntity
import com.felisreader.core.domain.repository.HistoryRepository

class DeleteHistoryItemUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(item: SearchHistoryEntity) {
        historyRepository.delete(item)
    }
}