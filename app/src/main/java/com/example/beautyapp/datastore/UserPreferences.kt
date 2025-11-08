package com.example.beautyapp.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_MOBILE = longPreferencesKey("mobile_number")
    }

    suspend fun saveLoginState(isLoggedIn: Boolean, mobile: Long) {
        context.dataStore.edit {
            it[KEY_IS_LOGGED_IN] = isLoggedIn
            it[KEY_MOBILE] = mobile
        }
    }

    suspend fun clearLoginState() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun getLoginState(): Pair<Boolean, Long?> {
        val prefs = context.dataStore.data.first()
        val loggedIn = prefs[KEY_IS_LOGGED_IN] ?: false
        val mobile = prefs[KEY_MOBILE]
        return Pair(loggedIn, mobile)
    }
}
