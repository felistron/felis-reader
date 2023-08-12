package com.felisreader.core.data.source.local

import androidx.room.*
import com.felisreader.core.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE content = :content")
    suspend fun deleteByContent(content: String)
}