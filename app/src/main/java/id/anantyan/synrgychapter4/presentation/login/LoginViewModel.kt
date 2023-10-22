package id.anantyan.synrgychapter4.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.domain.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private var _login = MutableLiveData<UIState<User>>()

    val login: LiveData<UIState<User>> = _login

    fun login(user: User) {
        viewModelScope.launch {
            usersUseCase.executeLogin(user).collect {
                _login.postValue(it)
            }
        }
    }
}