package com.felisreader.user.data.source.remote

import com.felisreader.core.domain.model.api.BasicResponse
import com.felisreader.user.domain.model.api.RatingBody
import com.felisreader.user.domain.model.api.ReadingStatusResponse
import com.felisreader.user.domain.model.api.UserRatingResponse
import com.felisreader.user.domain.model.api.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("user/me")
    suspend fun getLoggedUser(@Header("Authorization") authorization: String): Response<UserResponse>

    @GET("manga/status")
    suspend fun getReadingStatus(
        @Header("Authorization") authorization: String
    ): Response<ReadingStatusResponse>

    @GET("rating")
    suspend fun getLoggedUserRatings(
        @Header("Authorization") authorization: String,
        @Query("manga[]", encoded = true) manga: List<String>,
    ): Response<UserRatingResponse>

    @Headers("Content-Type: application/json")
    @POST("rating/{id}")
    suspend fun createOrUpdateMangaRating(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: RatingBody,
    ): Response<BasicResponse>

    @DELETE("rating/{id}")
    suspend fun deleteRating(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Response<BasicResponse>
}