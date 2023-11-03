package id.anantyan.synrgychapter4.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.data.local.repository.PreferenceRepositoryImpl
import id.anantyan.synrgychapter4.data.local.repository.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.PreferenceUseCase
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))
    private var _login = MutableLiveData<UIState<User>>()

    val login: LiveData<UIState<User>> = _login
    val getTheme = preferenceUseCase.executeGetTheme()
    val getLogin = runBlocking { preferenceUseCase.executeGetLogin().first() }

    fun login(user: User) {
        viewModelScope.launch {
            usersUseCase.executeLogin(user).collect {
                _login.postValue(it)
            }
        }
    }

    fun setTheme(value: Boolean) {
        viewModelScope.launch {
            preferenceUseCase.executeSetTheme(value)
        }
    }

    fun setLogin(value: Boolean) {
        viewModelScope.launch {
            preferenceUseCase.executeSetLogin(value)
        }
    }

    fun setUsrId(value: Long) {
        viewModelScope.launch {
            preferenceUseCase.executeSetUsrId(value)
        }
    }
}