package com.felisreader.chapter.data.source.remote

import com.felisreader.chapter.domain.model.api.Aggregate
import com.felisreader.chapter.domain.model.api.AtHomeResponse
import com.felisreader.chapter.domain.model.api.ChapterResponse
import com.felisreader.chapter.domain.model.api.ChapterListResponse
import com.felisreader.chapter.domain.model.api.AtHomeReportBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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
    ): Response<ChapterListResponse>

    @GET("at-home/server/{id}")
    suspend fun getChapterFeed(
        @Path("id") id: String,
        @Query("forcePort443") forcePort443: Boolean? = null
    ): Response<AtHomeResponse>

    @GET("manga/{id}/aggregate")
    suspend fun getAggregate(
        @Path("id") id: String,
        @Query("translatedLanguage[]", encoded = true) translatedLanguage: List<String>?,
        @Query("groups[]", encoded = true) groups: List<String>?
    ): Response<Aggregate>

    @GET("chapter/{id}")
    suspend fun getChapter(
        @Path("id") chapterId: String,
        @Query("includes[]", encoded = true) includes: List<String>?
    ): Response<ChapterResponse>

    @Headers("Content-Type: application/json")
    @POST("https://api.mangadex.network/report")
    suspend fun postReport(
        @Body body: AtHomeReportBody
    )

    @GET("chapter")
    suspend fun getChapterList(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("ids[]", encoded = true) ids: List<String>?,
        @Query("title") title: String?,
        @Query("groups[]", encoded = true) groups: List<String>?,
        @Query("uploader[]", encoded = true) uploader: List<String>?,
        @Query("manga") manga: String?,
        @Query("volume[]", encoded = true) volume: List<String>?,
        @Query("chapter[]", encoded = true) chapter: List<String>?,
        @Query("translatedLanguage[]", encoded = true) translatedLanguage: List<String>?,
        @Query("originalLanguage[]", encoded = true) originalLanguage: List<String>?,
        @Query("excludedOriginalLanguage[]", encoded = true) excludedOriginalLanguage: List<String>?,
        @Query("contentRating[]", encoded = true) contentRating: List<String>?,
        @Query("excludedGroups[]", encoded = true) excludedGroups: List<String>?,
        @Query("excludedUploaders[]", encoded = true) excludedUploaders: List<String>?,
        @Query("includeFutureUpdates") includeFutureUpdates: Int?,
        @Query("includeEmptyPages") includeEmptyPages: Int?,
        @Query("includeFuturePublishAt") includeFuturePublishAt: Int?,
        @Query("includeExternalUrl") includeExternalUrl: Int?,
        @Query("createdAtSince") createdAtSince: String?,
        @Query("updatedAtSince") updatedAtSince: String?,
        @Query("publishAtSince") publishAtSince: String?,
        @QueryMap(encoded = true) order: Map<String, String>?,
        @Query("includes[]", encoded = true) includes: List<String>?,
    ): Response<ChapterListResponse>
}