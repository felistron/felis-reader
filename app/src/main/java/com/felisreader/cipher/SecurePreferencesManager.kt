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
            "secret_shared_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAccessToken(accessToken: AccessToken, timestamp: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong("accessTimestamp", timestamp)
        editor.putString("accessToken", accessToken.accessToken)
        editor.putLong("expiresIn", accessToken.expiresIn)
        editor.putString("refreshToken", accessToken.refreshToken)
        editor.putLong("refreshExpiresIn", accessToken.refreshExpiresIn)
        editor.apply()
    }
}