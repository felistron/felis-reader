package com.felisreader.core.domain.repository

import com.felisreader.core.domain.model.MangaHistoryEntity

interface MangaHistoryRepository {
    suspend fun getAll(): List<MangaHistoryEntity>
    suspend fun insert(mangaId: String)
    suspend fun deleteById(mangaId: String)
}