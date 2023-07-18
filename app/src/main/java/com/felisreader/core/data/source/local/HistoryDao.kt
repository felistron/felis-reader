package com.felisreader.core.data.source.local

import androidx.room.*
import com.felisreader.core.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history WHERE id = :id")
    suspend fun getById(id: Int): SearchHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SearchHistoryEntity)

    @Delete
    suspend fun delete(item: SearchHistoryEntity)
}