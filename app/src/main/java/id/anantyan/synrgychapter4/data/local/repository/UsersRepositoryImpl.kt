package id.anantyan.synrgychapter4.data.local.repository

import android.app.Application
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.database.RoomDB
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.domain.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl(application: Application) : UsersRepository {

    private val usersDao = RoomDB.database(application).usersDao()

    override suspend fun login(user: User): Flow<UIState<User>> = flow {
        val users = usersDao.login(user.email, user.password)
        if (users != null) {
            emit(UIState.Success(users))
        } else {
            emit(UIState.Error("Akun yang anda masukan belum dibuat!"))
        }
    }

    override suspend fun checkUser(id: Long?): Flow<User> = flow {
        usersDao.checkUser(id)?.let { users ->
            emit(users)
        }
    }

    override suspend fun register(user: User): Flow<UIState<Long>> = flow {
        val users = usersDao.duplicateUser(user.email, user.username)
        if (users == null) {
            val results = usersDao.register(user)
            if (results != 0L) {
                emit(UIState.Success(results))
            } else {
                emit(UIState.Error("Gagal mendaftarkan akun!"))
            }
        } else {
            emit(UIState.Error("Akun yang anda masukan telah dibuat!"))
        }
    }
}