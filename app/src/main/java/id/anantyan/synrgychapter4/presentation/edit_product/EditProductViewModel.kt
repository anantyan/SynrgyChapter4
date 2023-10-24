package id.anantyan.synrgychapter4.presentation.edit_product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.domain.ProductsRepositoryImpl
import id.anantyan.synrgychapter4.domain.ProductsUseCase
import kotlinx.coroutines.launch

class EditProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private var _editProduct = MutableLiveData<Int>()
    private var _checkProduct = MutableLiveData<UIState<Product>>()

    val editProduct: LiveData<Int> = _editProduct
    val checkProduct: LiveData<UIState<Product>> = _checkProduct

    fun editProduct(product: Product) {
        viewModelScope.launch {
            productsUseCase.executeUpdate(product).collect {
                _editProduct.postValue(it)
            }
        }
    }

    fun checkProduct(id: Long?) {
        viewModelScope.launch {
            productsUseCase.executeCheckProduct(id).collect {
                _checkProduct.postValue(it)
            }
        }
    }
}