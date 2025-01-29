package com.felisreader.user.data.repository

import com.felisreader.user.data.source.remote.UserService
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.api.UserResponse
import com.felisreader.user.domain.repository.AuthRepository
import com.felisreader.user.domain.repository.UserRepository

class UserRepositoryImp(
    private val userService: UserService,
    private val authRepository: AuthRepository
) : UserRepository {
    override suspend fun getLoggedUser(): ApiResult<UserResponse> {
        val accessToken = authRepository.getAccessToken()
            ?: return ApiResult.Failure(401)

        val response = userService.getLoggedUser("Bearer $accessToken")

        val userResponse = response.body()
            ?: return ApiResult.Failure(response.code())

        return ApiResult.Success(userResponse)
    }
}