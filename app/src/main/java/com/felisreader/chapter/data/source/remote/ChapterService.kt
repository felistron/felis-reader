package com.felisreader.chapter.data.source.remote

import com.felisreader.chapter.domain.model.api.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ChapterService {
    @GET("manga/{id}/feed")
    suspend fun getMangaFeed(
        @Path("id") id: String,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("translatedLanguage[]", encoded = true) translatedLanguage: List<String>?,
        @Query("originalLanguage[]", encoded = true) originalLanguage: List<String>?,
        @Query("excludedOriginalLanguage[]", encoded = true) excludedOriginalLanguage: List<String>?,
        @Query("contentRating[]", encoded = true) contentRating: List<String>?,
        @Query("excludedGroups[]", encoded = true) excludedGroups: List<String>?,
        @Query("excludedUploaders[]", encoded = true) excludedUploaders: List<String>?,
        @Query("includeFutureUpdates") includeFutureUpdates: Int?,
        @Query("createdAtSince") createdAtSince: String?,
        @Query("updatedAtSince") updatedAtSince: String?,
        @Query("publishAtSince") publishAtSince: String?,
        @QueryMap(encoded = true) order: Map<String, String>?,
        @Query("includes[]", encoded = true) includes: List<String>?,
        @Query("includeEmptyPages") includeEmptyPages: Int?,
        @Query("includeFuturePublishAt") includeFuturePublishAt: Int?,
        @Query("includeExternalUrl") includeExternalUrl: Int?
    ): Response<FeedResponse>
}