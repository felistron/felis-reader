package com.felisreader.core.domain.repository

import com.felisreader.core.domain.model.ReadingHistoryEntity

interface ReadingHistoryRepository {
    suspend fun getAll(limit: Int, offset: Int): List<ReadingHistoryEntity>
    suspend fun insert(chapterId: String)
    suspend fun deleteById(chapterId: String)
}