package id.anantyan.synrgychapter4.domain

import kotlinx.coroutines.flow.Flow

class PreferenceUseCase(private val preferenceRepository: PreferenceRepository) {
    suspend fun executeSetTheme(value: Boolean) = preferenceRepository.setTheme(value)
    fun executeGetTheme(): Flow<Boolean> = preferenceRepository.getTheme()

    suspend fun executeSetLogin(value: Boolean) = preferenceRepository.setLogin(value)

    fun executeGetLogin(): Flow<Boolean> = preferenceRepository.getLogin()

    suspend fun executeSetUsrId(value: Long) = preferenceRepository.setUsrId(value)

    fun executeGetUsrId(): Flow<Long> = preferenceRepository.getUsrId()
}