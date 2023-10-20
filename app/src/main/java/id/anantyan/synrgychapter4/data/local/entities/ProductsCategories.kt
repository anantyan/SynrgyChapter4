package id.anantyan.synrgychapter4.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "tbl_products_categories",
    primaryKeys = ["product_id", "category_id"]
)
data class ProductsCategories(
    @ColumnInfo(name = "product_id")
    val productId: Long? = 0,

    @ColumnInfo(name = "category_id")
    val categoryId: Long? = 0
)
