package id.anantyan.synrgychapter4.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface ProductsDao {

    @Query("SELECT * FROM tbl_products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM tbl_products WHERE name LIKE :query OR price LIKE :query")
    suspend fun getQuery(query: String?): List<Product>

    @Insert
    suspend fun add(product: Product): Long

    @Update
    suspend fun update(product: Product): Int

    @Query("DELETE FROM tbl_products WHERE id=:id")
    suspend fun delete(id: Long?)
}