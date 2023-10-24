package id.anantyan.synrgychapter4.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

class DataStoreManager(private val context: Context) {
    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore by preferencesDataStore(DATASTORE_SETTINGS)
        private val THEME_DAY_NIGHT = booleanPreferencesKey("DAY_NIGHT_THEME")
    }

    fun setTheme(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.setValue(THEME_DAY_NIGHT, value)
        }
    }

    fun getTheme(): Flow<Boolean> = context.dataStore.getValue(THEME_DAY_NIGHT, false)
}

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> = this.data
    .catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun <T> DataStore<Preferences>.removeValue(key: Preferences.Key<T>) {
    this.edit { preferences ->
        preferences.remove(key)
    }
}

suspend fun DataStore<Preferences>.clear() {
    this.edit { preferences ->
        preferences.clear()
    }
}