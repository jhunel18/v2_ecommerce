package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username:String,
    val password:String,
    val confirmPassword:String
    )