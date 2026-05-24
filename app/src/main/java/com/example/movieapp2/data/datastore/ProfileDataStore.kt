package com.example.movieapp2.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfileDataStore(private val context: Context) {

    companion object {
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val RESUME_URL_KEY = stringPreferencesKey("resume_url")
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri")
    }

    val profileFlow: Flow<ProfileData> = context.dataStore.data.map { prefs ->
        ProfileData(
            fullName = prefs[FULL_NAME_KEY] ?: "",
            resumeUrl = prefs[RESUME_URL_KEY] ?: "",
            avatarUri = prefs[AVATAR_URI_KEY] ?: ""
        )
    }

    suspend fun saveProfile(fullName: String, resumeUrl: String, avatarUri: String) {
        context.dataStore.edit { prefs ->
            prefs[FULL_NAME_KEY] = fullName
            prefs[RESUME_URL_KEY] = resumeUrl
            prefs[AVATAR_URI_KEY] = avatarUri
        }
    }

    suspend fun saveAvatar(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[AVATAR_URI_KEY] = uri
        }
    }
}

data class ProfileData(
    val fullName: String = "",
    val resumeUrl: String = "",
    val avatarUri: String = ""
)
