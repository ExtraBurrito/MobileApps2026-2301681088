package com.example.mobileapps2025.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create the datastore file, it will be called "user_prefs"...
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }
    // English by default if NULL
    val currentLanguageFlow: Flow<String> = context.dataStore.data.map { preferences ->  preferences[LANGUAGE_KEY] ?: "English"
    }
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences -> preferences[LANGUAGE_KEY] = language
        }
    }
}