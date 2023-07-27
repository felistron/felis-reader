package com.felisreader.manga.data.source.remote

import com.felisreader.core.domain.*
import com.felisreader.manga.domain.model.api.MangaListResponse
import com.felisreader.manga.domain.model.api.MangaResponse
import com.felisreader.manga.domain.model.api.StatisticsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MangaService {
    @GET("manga/{id}/")
    suspend fun getMangaById(
        @Path("id") id: String,
        @Query("includes[]", encoded = true) includes: List<String>?
    ): Response<MangaResponse>

    @GET("manga/")
    suspend fun getMangaList(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("title") title: String?,
        @Query("authorOrArtist") authorOrArtist: String?,
        @Query("authors[]", encoded = true) authors: List<String>?,
        @Query("artists[]", encoded = true) artists: List<String>?,
        @Query("year") year: Int?,
        @Query("includedTags[]", encoded = true) includedTags: List<String>?,
        @Query("includedTagsMode") includedTagsMode: String?,
        @Query("excludedTags[]", encoded = true) excludedTags: List<String>?,
        @Query("excludedTagsMode") excludedTagsMode: String?,
        @Query("status[]", encoded = true) status: List<String>?,
        @Query("originalLanguage[]", encoded = true) originalLanguage: List<String>?,
        @Query("excludedOriginalLanguage[]", encoded = true) excludedOriginalLanguage: List<String>?,
        @Query("availableTranslatedLanguage[]", encoded = true) availableTranslatedLanguage: List<String>?,
        @Query("publicationDemographic[]", encoded = true) publicationDemographic: List<String>?,
        @Query("ids[]", encoded = true) ids: List<String>?,
        @Query("contentRating[]", encoded = true) contentRating: List<String>?,
        @Query("createdAtSince") createdAtSince: String?,
        @Query("updatedAtSince") updatedAtSince: String?,
        @QueryMap(encoded = true) order: Map<String, String>?,
        @Query("includes[]", encoded = true) includes: List<String>?,
        @Query("hasAvailableChapters") hasAvailableChapters: Boolean?,
        @Query("group") group: String?,
    ): Response<MangaListResponse>

    @GET("statistics/manga")
    suspend fun getStatistics(
        @Query("manga[]", encoded = true) manga: List<String>?
    ): Response<StatisticsResponse>

    @GET("statistics/manga/{id}")
    suspend fun getMangaStatistics(
        @Path("id") id: String
    ): Response<StatisticsResponse>

    companion object {
        const val BASE_URL: String = "https://api.mangadex.org/"
        const val UPLOADS_BASE_URL: String = "https://uploads.mangadex.org"
    }
}
