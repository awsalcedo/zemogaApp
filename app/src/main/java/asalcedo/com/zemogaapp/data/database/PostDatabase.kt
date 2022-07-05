package asalcedo.com.zemogaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import asalcedo.com.zemogaapp.data.database.dao.PostDao
import asalcedo.com.zemogaapp.data.database.entities.PostEntity
import asalcedo.com.zemogaapp.data.database.entities.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun getPostDao(): PostDao
}