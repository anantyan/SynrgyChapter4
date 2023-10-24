package id.anantyan.synrgychapter4.domain

import android.app.Application
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.data.local.database.RoomDB
import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepositoryImpl(application: Application) : ProductsRepository {

    private val productsDao = RoomDB.database(application).productsDao()
    override fun getAll(userId: Long?): Flow<List<Product>> = productsDao.getAll(userId)

    override fun getQuery(userId: Long?, query: String?): Flow<List<Product>> = flow { emit(productsDao.getQuery(userId, "%$query%")) }
    override suspend fun checkProduct(id: Long?): Flow<UIState<Product>> = flow {
        val product = productsDao.checkProduct(id)
        if (product != null) {
            emit(UIState.Success(product))
        } else {
            emit(UIState.Error("Data product belum ada!"))
        }
    }

    override suspend fun add(product: Product): Flow<Long> = flow { emit(productsDao.add(product)) }

    override suspend fun update(product: Product): Flow<Int> = flow { emit(productsDao.update(product)) }

    override suspend fun delete(id: Long?) = flow { emit(productsDao.delete(id)) }
}