package id.anantyan.synrgychapter4.domain

import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(private val productsRepository: ProductsRepository) {

    fun executeGetAll(): Flow<List<Product>> = productsRepository.getAll()

    fun executeGetQuery(query: String?): Flow<List<Product>> = productsRepository.getQuery(query)

    suspend fun executeAdd(product: Product): Flow<Long> = productsRepository.add(product)

    suspend fun executeUpdate(product: Product): Flow<Int> = productsRepository.update(product)

    suspend fun executeDelete(id: Long) = productsRepository.delete(id)
}