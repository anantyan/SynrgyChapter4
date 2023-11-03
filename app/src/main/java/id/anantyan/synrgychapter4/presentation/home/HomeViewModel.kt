package id.anantyan.synrgychapter4.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.data.local.repository.PreferenceRepositoryImpl
import id.anantyan.synrgychapter4.data.local.repository.ProductsRepositoryImpl
import id.anantyan.synrgychapter4.domain.ProductsUseCase
import id.anantyan.synrgychapter4.data.local.repository.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.PreferenceUseCase
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))
    private var _getQuery = MutableLiveData<List<Product>>()
    private var _checkUser = MutableLiveData<User>()
    private var _deleteProduct = MutableLiveData<Unit>()

    val getQuery: LiveData<List<Product>> = _getQuery
    val checkUser: LiveData<User> = _checkUser
    val deleteProduct: LiveData<Unit> = _deleteProduct
    private val getUsrId = preferenceUseCase.executeGetUsrId()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAll(): LiveData<List<Product>> = getUsrId.flatMapLatest {
        productsUseCase.executeGetAll(it)
    }.asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getQuery(query: String?) {
        viewModelScope.launch {
            getUsrId.flatMapLatest {
                productsUseCase.executeGetQuery(it, query)
            }.collect {
                _getQuery.postValue(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun checkUser() {
        viewModelScope.launch {
            getUsrId.flatMapLatest {
                usersUseCase.executeCheckUser(it)
            }.collect {
                _checkUser.postValue(it)
            }
        }
    }

    fun deleteProduct(id: Long?) {
        viewModelScope.launch {
            productsUseCase.executeDelete(id).collect {
                _deleteProduct.postValue(it)
            }
        }
    }
}