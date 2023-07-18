package com.felisreader.core.data.repository

import com.felisreader.core.data.source.local.HistoryDao
import com.felisreader.core.domain.model.SearchHistoryEntity
import com.felisreader.core.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImp(
    private val dao: HistoryDao
) : HistoryRepository {
    override fun getAll(): Flow<List<SearchHistoryEntity>> {
        return dao.getAll()
    }

    override suspend fun insert(item: SearchHistoryEntity) {
        return dao.insertOrUpdate(item)
    }

    override suspend fun delete(item: SearchHistoryEntity) {
        return dao.delete(item)
    }
}