package com.felisreader.user.data.repository

import com.felisreader.user.data.source.remote.UserService
import com.felisreader.user.domain.model.AccessToken
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.repository.UserRepository

class UserRepositoryImp(
    private val userService: UserService
) : UserRepository {
    override suspend fun getAccessToken(query: AccessTokenQuery): ApiResult<AccessToken> {
        val response = userService.getAccessToken(
            grantType = "password",
            username = query.username,
            password = query.password,
            clientId = query.clientId,
            clientSecret = query.clientSecret
        )

        val accessToken = response.body()
            ?: return ApiResult.Failure(response.code())

        return ApiResult.Success(accessToken)
    }
}