package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.ProductEntity
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.UserEntity

@Dao
interface ProductDAO {
    @Insert
    fun insert(productEntity: ProductEntity)

    @Query("SELECT * FROM products ORDER BY price ASC")
    fun getAllProducts(): List<ProductEntity>

//    @Query("SELECT * FROM tbl_Lessons ORDER BY lessonNumber ASC")
//    fun getAllLessons(): LiveData<List<LessonEntity>>

//    @Query("DELETE FROM products")
//    fun deleteProduct():LiveData<List<ProductEntity>>
}