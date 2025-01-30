package com.felisreader.user.data.source.remote

import com.felisreader.user.domain.model.api.ReadingHistoryResponse
import com.felisreader.user.domain.model.api.ReadingStatusResponse
import com.felisreader.user.domain.model.api.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("user/me")
    suspend fun getLoggedUser(@Header("Authorization") authorization: String): Response<UserResponse>

    @GET("manga/status")
    suspend fun getReadingStatus(
        @Header("Authorization") authorization: String
    ): Response<ReadingStatusResponse>

    @GET("user/history")
    suspend fun getReadingHistory(
        @Header("Authorization") authorization: String
    ): Response<ReadingHistoryResponse>
}