package com.felisreader.core.data.repository

import com.felisreader.core.data.source.local.MangaHistoryDao
import com.felisreader.core.domain.model.MangaHistoryEntity
import com.felisreader.core.domain.repository.MangaHistoryRepository

class MangaHistoryRepositoryImp(
    private val dao: MangaHistoryDao
) : MangaHistoryRepository {
    override suspend fun getAll(limit: Int, offset: Int): List<MangaHistoryEntity> {
        return dao.getAll(limit, offset)
    }

    override suspend fun insert(mangaId: String) {
        dao.insertOrUpdate(MangaHistoryEntity(mangaId, System.currentTimeMillis()))
    }

    override suspend fun deleteById(mangaId: String) {
        dao.deleteById(mangaId)
    }

}