package id.anantyan.synrgychapter4.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.data.local.repository.PreferenceRepositoryImpl
import id.anantyan.synrgychapter4.data.local.repository.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.PreferenceUseCase
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))
    private var _getUsers = MutableLiveData<List<ProfileModel>>()

    val getUsers: LiveData<List<ProfileModel>> = _getUsers
    val getTheme = preferenceUseCase.executeGetTheme()
    val getUsrId = preferenceUseCase.executeGetUsrId()

    fun getUsers(id: Long?) {
        viewModelScope.launch {
            usersUseCase.executeCheckUser(id).collect {
                val items = listOf(
                    ProfileModel(R.drawable.round_key_24, "ID", it.id.toString()),
                    ProfileModel(R.drawable.round_person_24, "Name", it.name),
                    ProfileModel(R.drawable.round_verified_user_24, "Username", it.username),
                    ProfileModel(R.drawable.round_alternate_email_24, "Email", it.email)
                )
                _getUsers.postValue(items)
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