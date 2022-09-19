package com.radius.dev.data.source.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_keys")

    companion object {
        private val API_FETCH_TIME = longPreferencesKey("api_fetch_time")
    }

    val apiFetchTime: Flow<Long?> = context.dataStore.data.map { keys -> keys[API_FETCH_TIME] }

    suspend fun setApiFetchTime() {
        context.dataStore.edit { keys ->
            keys[API_FETCH_TIME] = System.currentTimeMillis()
        }
    }
}