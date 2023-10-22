package id.anantyan.synrgychapter4.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.domain.ProductsRepositoryImpl
import id.anantyan.synrgychapter4.domain.ProductsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private var _getQuery = MutableLiveData<List<Product>>()

    val getAll: LiveData<List<Product>> = productsUseCase.executeGetAll().asLiveData()
    val getQuery: LiveData<List<Product>> = _getQuery

    fun getQuery(query: String?) {
        viewModelScope.launch {
            productsUseCase.executeGetQuery(query).collect {
                _getQuery.postValue(it)
            }
        }
    }
}