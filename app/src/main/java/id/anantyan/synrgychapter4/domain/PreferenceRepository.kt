package id.anantyan.synrgychapter4.domain

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun setTheme(value: Boolean)
    fun getTheme(): Flow<Boolean>
    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setUsrId(value: Long)
    fun getUsrId(): Flow<Long>
}