package com.felisreader.core.domain.repository

import com.felisreader.core.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAll(): Flow<List<SearchHistoryEntity>>
    suspend fun insert(item: SearchHistoryEntity)
    suspend fun delete(item: SearchHistoryEntity)
}