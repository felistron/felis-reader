package com.felisreader.library.presentation.follows

import com.felisreader.user.domain.model.api.ReadingStatus

sealed class FollowsEvent {
    data class SetSelectedTab(val index: Int): FollowsEvent()
    data class LoadReadingStatus(val status: ReadingStatus): FollowsEvent()
}
