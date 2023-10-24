package id.anantyan.synrgychapter4.domain

import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class UsersUseCase(private val usersRepository: UsersRepository) {
    suspend fun executeLogin(user: User): Flow<UIState<User>> = usersRepository.login(user)
    suspend fun executeCheckUser(id: Long?): Flow<User> = usersRepository.checkUser(id)
    suspend fun executeRegister(user: User): Flow<UIState<Long>> = usersRepository.register(user)
}