package com.felisreader.core.domain.use_case

import com.felisreader.core.domain.model.MangaHistoryEntity
import com.felisreader.core.domain.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

data class HistoryUseCases(
    val getHistory: () -> Flow<List<SearchHistoryEntity>>,
    val addItem: suspend (content: String, timestamp: Long) -> Unit,
    val deleteItem: suspend (content: String) -> Unit,
    val getMangaHistory: suspend (limit: Int, offset: Int) -> List<MangaHistoryEntity>,
    val addMangaItem: suspend (mangaId: String) -> Unit,
    val deleteMangaItem: suspend (mangaId: String) -> Unit
)