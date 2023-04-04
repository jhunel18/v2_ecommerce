package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.UserEntity

@Dao
interface UserDAO {
    @Insert
    fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username:String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun findByUsernameAndPassword(username: String, password: String): UserEntity?


}