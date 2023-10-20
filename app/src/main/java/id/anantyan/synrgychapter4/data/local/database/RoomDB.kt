package id.anantyan.synrgychapter4.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.anantyan.synrgychapter4.data.local.entities.Category
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.data.local.entities.ProductsCategories
import id.anantyan.synrgychapter4.data.local.entities.Users

@Database(
    entities = [
        Users::class,
        Product::class,
        Category::class,
        ProductsCategories::class
    ], version = 1, exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun database(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "db_app"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun productsDao()
    abstract fun usersDao()
}