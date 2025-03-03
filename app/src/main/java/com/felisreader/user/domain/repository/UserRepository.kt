package com.felisreader.user.domain.repository

import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.api.ReadingStatus
import com.felisreader.user.domain.model.api.UserRatingResponse
import com.felisreader.user.domain.model.api.ReadingStatusListResponse
import com.felisreader.user.domain.model.api.ReadingStatusResponse
import com.felisreader.user.domain.model.api.UserResponse

interface UserRepository {
    suspend fun getLoggedUser(): ApiResult<UserResponse>
    suspend fun getAllReadingStatus(): ApiResult<ReadingStatusListResponse>
    suspend fun getReadingStatus(mangaId: String): ApiResult<ReadingStatusResponse>
    suspend fun updateReadingStatus(mangaId: String, readingStatus: ReadingStatus?): ApiResult<Unit>
    suspend fun getRatings(manga: List<String>): ApiResult<UserRatingResponse>
    suspend fun createOrUpdateRating(mangaId: String, rating: Int): ApiResult<Unit>
    suspend fun deleteRating(mangaId: String): ApiResult<Unit>
}