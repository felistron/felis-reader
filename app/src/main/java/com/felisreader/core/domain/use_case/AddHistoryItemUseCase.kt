package com.felisreader.core.domain.use_case

import com.felisreader.core.domain.model.SearchHistoryEntity
import com.felisreader.core.domain.repository.HistoryRepository

class AddHistoryItemUseCase(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(content: String, timestamp: Long) {
        historyRepository.insert(
            SearchHistoryEntity(
                content = content,
                timestamp = timestamp
            )
        )
    }
}