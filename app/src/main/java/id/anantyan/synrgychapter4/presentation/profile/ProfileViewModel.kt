package id.anantyan.synrgychapter4.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.domain.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private var _getUsers = MutableLiveData<List<ProfileModel>>()

    val getUsers: LiveData<List<ProfileModel>> = _getUsers

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
}