package id.anantyan.synrgychapter4.domain

import android.app.Application
import id.anantyan.synrgychapter4.data.local.database.RoomDB
import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepositoryImpl(application: Application) : ProductsRepository {

    private val productsDao = RoomDB.database(application).productsDao()
    override fun getAll(): Flow<List<Product>> = productsDao.getAll()

    override fun getQuery(query: String?): Flow<List<Product>> = flow { emit(productsDao.getQuery(query)) }

    override suspend fun add(product: Product): Flow<Long> = flow { emit(productsDao.add(product)) }

    override suspend fun update(product: Product): Flow<Int> = flow { emit(productsDao.update(product)) }

    override suspend fun delete(id: Long) = flow { emit(productsDao.delete(id)) }
}