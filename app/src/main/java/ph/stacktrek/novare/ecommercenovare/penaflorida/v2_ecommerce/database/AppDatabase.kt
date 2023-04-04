package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.dao.ProductDAO
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.dao.UserDAO
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.ProductEntity
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.UserEntity

@Database(entities = [UserEntity::class, ProductEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun productDao():ProductDAO
}