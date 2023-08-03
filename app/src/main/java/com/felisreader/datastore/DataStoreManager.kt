package com.felisreader.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val DATASTORE_NAME = "USER_PREFERENCES"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreManager(private val context: Context) {

    companion object {
        val SHOW_WELCOME = booleanPreferencesKey("SHOW_WELCOME")
    }

    suspend fun savePreferences(userPref: UserPreferences) {
        context.dataStore.edit {
            it[SHOW_WELCOME] = userPref.showWelcome
        }
    }

    fun getPreferences() = context.dataStore.data.map {
        UserPreferences(
            showWelcome = it[SHOW_WELCOME] ?: true
        )
    }

    suspend fun clearPreferences() = context.dataStore.edit {
        it.clear()
    }
}