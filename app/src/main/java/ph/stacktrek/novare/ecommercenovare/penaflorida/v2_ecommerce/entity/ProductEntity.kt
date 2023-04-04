package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "products")
class ProductEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName:String,
    val description:String,
    val price:Double,
    val image:ByteArray?=null
)