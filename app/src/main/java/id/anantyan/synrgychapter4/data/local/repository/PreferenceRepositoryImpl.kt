package id.anantyan.synrgychapter4.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.anantyan.synrgychapter4.common.getValue
import id.anantyan.synrgychapter4.common.setValue
import id.anantyan.synrgychapter4.domain.PreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PreferenceRepositoryImpl(private val context: Context) : PreferenceRepository {
    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_SETTINGS)
        private val THEME_DAY_NIGHT = booleanPreferencesKey("DAY_NIGHT_THEME")
        private val LOOGED = booleanPreferencesKey("LOGIN")
        private val USR_ID = longPreferencesKey("USR_ID")
    }

    override suspend fun setTheme(value: Boolean) {
        context.dataStore.setValue(THEME_DAY_NIGHT, value)
    }

    override fun getTheme(): Flow<Boolean> = context.dataStore.getValue(THEME_DAY_NIGHT, false)

    override suspend fun setLogin(value: Boolean) {
        context.dataStore.setValue(LOOGED, value)
    }

    override fun getLogin(): Flow<Boolean> = context.dataStore.getValue(LOOGED, false)

    override suspend fun setUsrId(value: Long) {
        context.dataStore.setValue(USR_ID, value)
    }

    override fun getUsrId(): Flow<Long> = context.dataStore.getValue(USR_ID, -1L)
}