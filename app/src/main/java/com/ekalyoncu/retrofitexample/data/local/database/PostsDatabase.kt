package com.ekalyoncu.retrofitexample.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ekalyoncu.retrofitexample.data.local.database.converter.DaoConverter
import com.ekalyoncu.retrofitexample.data.local.database.dao.PostDao
import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.utils.Constants

/**
 * Created by ekalyoncu on 16.10.2022.
 */

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
@TypeConverters(DaoConverter::class)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        private var instance: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, PostsDatabase::class.java, Constants.TABLE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

    }
}