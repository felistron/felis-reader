package com.felisreader.user.domain.repository

import com.felisreader.user.domain.model.AccessToken
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.RefreshTokenQuery

interface AuthRepository {
    suspend fun createAccessToken(query: AccessTokenQuery): ApiResult<AccessToken>
    suspend fun refreshToken(query: RefreshTokenQuery): ApiResult<AccessToken>
    suspend fun getAccessToken(): String?
}