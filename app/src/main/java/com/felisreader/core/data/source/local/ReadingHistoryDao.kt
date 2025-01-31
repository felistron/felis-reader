package com.felisreader.core.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felisreader.core.domain.model.ReadingHistoryEntity

@Dao
interface ReadingHistoryDao {
    @Query("SELECT * FROM reading_history ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<ReadingHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: ReadingHistoryEntity)

    @Query("DELETE FROM reading_history WHERE id = :chapterId")
    suspend fun deleteById(chapterId: String)
}