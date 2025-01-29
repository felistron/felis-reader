package com.felisreader.cipher

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.felisreader.user.domain.model.AccessToken

class SecurePreferencesManager(
    context: Context
) {
    private var sharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            SECRET_SHARED_PREFERENCES_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAccessToken(accessToken: AccessToken, timestamp: Long, clientId: String, clientSecret: String) {
        val editor = sharedPreferences.edit()
        editor.putLong(ACCESS_TIMESTAMP_KEY, timestamp)
        editor.putString(ACCESS_TOKEN_KEY, accessToken.accessToken)
        editor.putLong(EXPIRES_IN_KEY, accessToken.expiresIn)
        editor.putString(REFRESH_TOKEN_KEY, accessToken.refreshToken)
        editor.putLong(REFRESH_EXPIRES_IN_KEY, accessToken.refreshExpiresIn)
        editor.putString(CLIENT_ID_KEY, clientId)
        editor.putString(CLIENT_SECRET_KEY, clientSecret)
        editor.apply()
    }

    /**
     * @return Timestamp in milliseconds when the access token was created.
     */
    fun getAccessTimestamp(): Long {
        return sharedPreferences.getLong(ACCESS_TIMESTAMP_KEY, DEFAULT_TIMESTAMP)
    }


    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    /**
     * @return Time in seconds until the `accessToken` expires.
     */
    fun getExpiresIn(): Long {
        return sharedPreferences.getLong(EXPIRES_IN_KEY, DEFAULT_EXPIRATION)
    }

    /**
     * @return Time in seconds until the `refreshToken` expires.
     */
    fun getRefreshExpiresIn(): Long {
        return sharedPreferences.getLong(REFRESH_EXPIRES_IN_KEY, DEFAULT_EXPIRATION)
    }

    fun getClientId(): String? {
        return sharedPreferences.getString(CLIENT_ID_KEY, null)
    }

    fun getClientSecret(): String? {
        return sharedPreferences.getString(CLIENT_SECRET_KEY, null)
    }

    companion object {
        const val SECRET_SHARED_PREFERENCES_FILENAME = "secret_shared_preferences"
        const val DEFAULT_TIMESTAMP: Long = 0
        const val DEFAULT_EXPIRATION: Long = 0

        const val ACCESS_TOKEN_KEY = "accessToken"
        const val REFRESH_TOKEN_KEY = "refreshToken"
        const val EXPIRES_IN_KEY = "expiresIn"
        const val REFRESH_EXPIRES_IN_KEY = "refreshExpiresIn"
        const val ACCESS_TIMESTAMP_KEY = "accessTimestamp"
        const val CLIENT_ID_KEY = "clientId"
        const val CLIENT_SECRET_KEY = "clientSecret"

    }
}