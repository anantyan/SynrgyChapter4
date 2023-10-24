package id.anantyan.synrgychapter4.domain

import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun login(user: User): Flow<UIState<User>>
    suspend fun checkUser(id: Long?): Flow<User>
    suspend fun register(user: User): Flow<UIState<Long>>
}