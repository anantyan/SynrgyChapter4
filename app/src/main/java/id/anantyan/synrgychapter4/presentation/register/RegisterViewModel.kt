package id.anantyan.synrgychapter4.presentation.register

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

class RegisterViewModel(applications: Application) : AndroidViewModel(applications) {

    private val usersUseCase: UsersUseCase = UsersUseCase(UsersRepositoryImpl(applications))
    private var _register = MutableLiveData<UIState<Long>>()

    val register: LiveData<UIState<Long>> = _register

    fun register(user: User) {
        viewModelScope.launch {
            usersUseCase.executeRegister(user).collect {
                _register.postValue(it)
            }
        }
    }
}