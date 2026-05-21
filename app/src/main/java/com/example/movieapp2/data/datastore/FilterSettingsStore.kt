package com.example.movieapp2.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class FilterSettingsStore(private val context: Context) {

    companion object {
        private val SELECTED_CATEGORY = stringPreferencesKey("selected_category")
        private val MINIMUM_SCORE = intPreferencesKey("minimum_score")
    }

    val settingsFlow: Flow<FilterParams> = context.dataStore.data.map { prefs ->
        FilterParams(
            category = prefs[SELECTED_CATEGORY] ?: "Все",
            minScore = prefs[MINIMUM_SCORE] ?: 0
        )
    }

    suspend fun saveCategory(category: String) {
        context.dataStore.edit { prefs ->
            prefs[SELECTED_CATEGORY] = category
        }
    }

    suspend fun saveMinScore(score: Int) {
        context.dataStore.edit { prefs ->
            prefs[MINIMUM_SCORE] = score
        }
    }
}

data class FilterParams(
    val category: String,
    val minScore: Int
)