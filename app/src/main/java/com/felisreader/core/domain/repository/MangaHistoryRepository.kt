package com.felisreader.core.domain.repository

import com.felisreader.core.domain.model.MangaHistoryEntity

interface MangaHistoryRepository {
    suspend fun getAll(): List<MangaHistoryEntity>
    suspend fun insert(item: MangaHistoryEntity)
    suspend fun deleteById(mangaId: String)
}