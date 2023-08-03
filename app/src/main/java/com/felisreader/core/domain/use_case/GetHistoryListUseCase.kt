package com.felisreader.core.domain.use_case

import com.felisreader.core.domain.model.SearchHistoryEntity
import com.felisreader.core.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryListUseCase(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke(): Flow<List<SearchHistoryEntity>> {
        return historyRepository.getAll()
    }
}
