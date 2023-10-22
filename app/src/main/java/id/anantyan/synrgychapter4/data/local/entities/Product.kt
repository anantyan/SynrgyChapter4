package id.anantyan.synrgychapter4.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tbl_products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "quantity")
    val quantity: Long? = null,

    @ColumnInfo(name = "price")
    val price: Int? = null
)

data class ProductWithCategories(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsCategories::class)
    )
    val categories: List<Category>
)


