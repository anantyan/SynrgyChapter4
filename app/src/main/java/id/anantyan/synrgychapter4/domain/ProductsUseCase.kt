package id.anantyan.synrgychapter4.domain

import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(private val productsRepository: ProductsRepository) {

    fun executeGetAll(userId: Long?): Flow<List<Product>> = productsRepository.getAll(userId)

    fun executeGetQuery(userId: Long?, query: String?): Flow<List<Product>> = productsRepository.getQuery(userId, query)

    suspend fun executeCheckProduct(id: Long?): Flow<UIState<Product>> = productsRepository.checkProduct(id)

    suspend fun executeAdd(product: Product): Flow<Long> = productsRepository.add(product)

    suspend fun executeUpdate(product: Product): Flow<Int> = productsRepository.update(product)

    suspend fun executeDelete(id: Long?) = productsRepository.delete(id)
}