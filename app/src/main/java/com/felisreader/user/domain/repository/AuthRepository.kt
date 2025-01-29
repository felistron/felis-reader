package com.felisreader.user.domain.repository

import com.felisreader.user.domain.model.AccessToken
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.RefreshTokenQuery

interface AuthRepository {
    suspend fun getAccessToken(query: AccessTokenQuery): ApiResult<AccessToken>
    suspend fun refreshToken(query: RefreshTokenQuery): ApiResult<AccessToken>
    suspend fun isLoggedIn(): Boolean
}