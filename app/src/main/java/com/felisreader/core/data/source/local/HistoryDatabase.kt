package com.felisreader.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felisreader.core.domain.model.MangaHistoryEntity
import com.felisreader.core.domain.model.ReadingHistoryEntity
import com.felisreader.core.domain.model.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
        MangaHistoryEntity::class,
        ReadingHistoryEntity::class,
               ],
    version = 4
)
abstract class HistoryDatabase: RoomDatabase() {
    abstract val historyDao: HistoryDao
    abstract val mangaHistoryDao: MangaHistoryDao
    abstract val readingHistoryDao: ReadingHistoryDao

    companion object {
        const val DATABASE_NAME = "history_db"
    }
}