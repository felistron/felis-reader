package com.felisreader.core.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felisreader.core.domain.model.MangaHistoryEntity

@Dao
interface MangaHistoryDao {
    @Query("SELECT * FROM manga_history ORDER BY timestamp DESC")
    suspend fun getAll(): List<MangaHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: MangaHistoryEntity)

    @Query("DELETE FROM manga_history WHERE id = :mangaId")
    suspend fun deleteById(mangaId: String)
}