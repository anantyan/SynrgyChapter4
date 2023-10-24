package id.anantyan.synrgychapter4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.anantyan.synrgychapter4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM tbl_products WHERE user_id=:userId")
    fun getAll(userId: Long?): Flow<List<Product>>

    @Query("SELECT * FROM tbl_products WHERE user_id=:userId AND (name LIKE :query OR price LIKE :query)")
    suspend fun getQuery(userId: Long?, query: String?): List<Product>

    @Query("SELECT * FROM tbl_products WHERE id=:id")
    suspend fun checkProduct(id: Long?): Product?

    @Insert
    suspend fun add(product: Product): Long

    @Update
    suspend fun update(product: Product): Int

    @Query("DELETE FROM tbl_products WHERE id=:id")
    suspend fun delete(id: Long?)
}