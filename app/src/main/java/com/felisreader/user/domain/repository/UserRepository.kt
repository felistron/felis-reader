package com.felisreader.user.domain.repository

import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.api.UserResponse

interface UserRepository {
    suspend fun getLoggedUser(): ApiResult<UserResponse>
}