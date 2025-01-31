package com.felisreader.core.data.repository

import com.felisreader.core.data.source.local.ReadingHistoryDao
import com.felisreader.core.domain.model.ReadingHistoryEntity
import com.felisreader.core.domain.repository.ReadingHistoryRepository

class ReadingHistoryRepositoryImp(
    private val dao: ReadingHistoryDao
): ReadingHistoryRepository {
    override suspend fun getAll(limit: Int, offset: Int): List<ReadingHistoryEntity> {
        return dao.getAll(limit, offset)
    }

    override suspend fun insert(chapterId: String) {
        dao.insertOrUpdate(ReadingHistoryEntity(chapterId, System.currentTimeMillis()))
    }

    override suspend fun deleteById(chapterId: String) {
        dao.deleteById(chapterId)
    }
}