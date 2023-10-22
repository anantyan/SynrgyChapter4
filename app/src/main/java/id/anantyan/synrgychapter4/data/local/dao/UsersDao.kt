package id.anantyan.synrgychapter4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.anantyan.synrgychapter4.data.local.entities.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM tbl_users WHERE email=:email AND password=:password")
    suspend fun login(email: String?, password: String?): User

    @Query("SELECT * FROM tbl_users WHERE email=:email OR username=:username")
    suspend fun duplicateUser(email: String?, username: String?): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun register(item: User): Long
}