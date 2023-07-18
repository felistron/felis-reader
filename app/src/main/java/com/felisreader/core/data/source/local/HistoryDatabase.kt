package com.felisreader.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felisreader.core.domain.model.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 2
)
abstract class HistoryDatabase: RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        const val DATABASE_NAME = "history_db"
    }
}