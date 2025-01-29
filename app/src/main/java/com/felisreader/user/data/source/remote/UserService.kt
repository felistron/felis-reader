package com.felisreader.user.data.source.remote

import com.felisreader.user.domain.model.api.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("user/me")
    suspend fun getLoggedUser(@Header("Authorization") authorization: String): Response<UserResponse>
}