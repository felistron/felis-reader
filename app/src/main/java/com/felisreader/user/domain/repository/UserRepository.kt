package com.felisreader.user.domain.repository

import com.felisreader.user.domain.model.AccessToken
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult

interface UserRepository {
    suspend fun getAccessToken(query: AccessTokenQuery): ApiResult<AccessToken>
}