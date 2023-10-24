package id.anantyan.synrgychapter4.presentation.add_product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.domain.ProductsRepositoryImpl
import id.anantyan.synrgychapter4.domain.ProductsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private var _addProduct = MutableLiveData<Long>()

    val addProduct: LiveData<Long> = _addProduct

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productsUseCase.executeAdd(product).collect {
                _addProduct.postValue(it)
            }
        }
    }
}