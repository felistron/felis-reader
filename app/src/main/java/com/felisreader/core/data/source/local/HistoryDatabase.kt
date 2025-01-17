package com.felisreader.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felisreader.core.domain.model.MangaHistoryEntity
import com.felisreader.core.domain.model.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
        MangaHistoryEntity::class,
               ],
    version = 3
)
abstract class HistoryDatabase: RoomDatabase() {
    abstract val historyDao: HistoryDao
    abstract val mangaHistoryDao: MangaHistoryDao

    companion object {
        const val DATABASE_NAME = "history_db"
    }
}