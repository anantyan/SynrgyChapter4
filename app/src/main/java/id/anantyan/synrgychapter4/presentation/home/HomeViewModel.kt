package id.anantyan.synrgychapter4.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.domain.ProductsRepositoryImpl
import id.anantyan.synrgychapter4.domain.ProductsUseCase
import id.anantyan.synrgychapter4.domain.UsersRepositoryImpl
import id.anantyan.synrgychapter4.domain.UsersUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private val usersUseCase = UsersUseCase(UsersRepositoryImpl(application))
    private var _getQuery = MutableLiveData<List<Product>>()
    private var _checkUser = MutableLiveData<User>()
    private var _deleteProduct = MutableLiveData<Unit>()

    val getQuery: LiveData<List<Product>> = _getQuery
    val checkUser: LiveData<User> = _checkUser
    val deleteProduct: LiveData<Unit> = _deleteProduct

    fun getAll(userId: Long?): LiveData<List<Product>> = productsUseCase.executeGetAll(userId).asLiveData()

    fun getQuery(userId: Long?, query: String?) {
        viewModelScope.launch {
            productsUseCase.executeGetQuery(userId, query).collect {
                _getQuery.postValue(it)
            }
        }
    }

    fun checkUser(id: Long?) {
        viewModelScope.launch {
            usersUseCase.executeCheckUser(id).collect {
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