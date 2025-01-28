package com.felisreader.user.data.source.remote

import com.felisreader.user.domain.model.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @FormUrlEncoded
    @POST("realms/mangadex/protocol/openid-connect/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Response<AccessToken>

    companion object {
        const val AUTH_BASE_URL: String = "https://auth.mangadex.org/"
    }
}