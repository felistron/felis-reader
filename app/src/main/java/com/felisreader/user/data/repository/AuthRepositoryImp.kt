package com.felisreader.user.data.repository

import com.felisreader.cipher.SecurePreferencesManager
import com.felisreader.user.data.source.remote.AuthService
import com.felisreader.user.domain.model.AccessToken
import com.felisreader.user.domain.model.AccessTokenQuery
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.RefreshTokenQuery
import com.felisreader.user.domain.repository.AuthRepository

class AuthRepositoryImp(
    private val authService: AuthService,
    private val securePrefsManager: SecurePreferencesManager,
) : AuthRepository {
    override suspend fun createAccessToken(query: AccessTokenQuery): ApiResult<AccessToken> {
        val response = authService.getAccessToken(
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

    override suspend fun refreshToken(query: RefreshTokenQuery): ApiResult<AccessToken> {
        val response = authService.refreshToken(
            grantType = "refresh_token",
            refreshToken = query.refreshToken,
            clientId = query.clientId,
            clientSecret = query.clientSecret
        )

        val accessToken = response.body()
            ?: return ApiResult.Failure(response.code())

        return ApiResult.Success(accessToken)
    }

    override suspend fun getAccessToken(): String? {
        val currentTimestamp = System.currentTimeMillis()

        val accessTimestamp = securePrefsManager.getAccessTimestamp()
        val accessToken = securePrefsManager.getAccessToken()
        val refreshToken = securePrefsManager.getRefreshToken()
        val expiresIn = securePrefsManager.getExpiresIn()
        val refreshExpiresIn = securePrefsManager.getRefreshExpiresIn()
        val clientId = securePrefsManager.getClientId()
        val clientSecret = securePrefsManager.getClientSecret()

        if (
            accessTimestamp == SecurePreferencesManager.DEFAULT_TIMESTAMP ||
            accessToken == null ||
            refreshToken == null ||
            expiresIn == SecurePreferencesManager.DEFAULT_EXPIRATION ||
            refreshExpiresIn == SecurePreferencesManager.DEFAULT_EXPIRATION ||
            clientId == null ||
            clientSecret == null
        ) return null

        // Check if the accessToken has expired
        if (accessTimestamp + expiresIn * 1000 < currentTimestamp) {

            // Check if the refreshToken has expired
            if (accessTimestamp + refreshExpiresIn * 1000 < currentTimestamp) return null

            // Refresh the token
            val newToken = refreshToken(RefreshTokenQuery(
                refreshToken = refreshToken,
                clientId = clientId,
                clientSecret = clientSecret
            ))

            when (newToken) {
                is ApiResult.Success -> {
                    securePrefsManager.saveAccessToken(newToken.body, currentTimestamp, clientId, clientSecret)
                    return newToken.body.accessToken
                }
                is ApiResult.Failure -> return null
            }
        }

        return accessToken
    }
}