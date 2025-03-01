package com.felisreader.library.presentation.follows

import com.felisreader.manga.domain.model.Manga
import com.felisreader.user.domain.model.api.ReadingStatus

data class FollowsState(
    val selectedTab: Int = 0,
    val statuses: Map<String, ReadingStatus>? = null,
    val reading: List<Manga>? = null,
    val onHold: List<Manga>? = null,
    val planToRead: List<Manga>? = null,
    val dropped: List<Manga>? = null,
    val reReading: List<Manga>? = null,
    val completed: List<Manga>? = null,
)
