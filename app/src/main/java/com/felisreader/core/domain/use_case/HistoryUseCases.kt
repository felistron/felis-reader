package com.felisreader.core.domain.use_case

data class HistoryUseCases(
    val getHistory: GetHistoryListUseCase,
    val addItem: AddHistoryItemUseCase,
    val deleteItem: DeleteHistoryItemUseCase
)