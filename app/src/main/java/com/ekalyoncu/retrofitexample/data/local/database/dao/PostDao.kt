package com.ekalyoncu.retrofitexample.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ekalyoncu.retrofitexample.data.local.database.base.BaseDao
import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.utils.Constants

/**
 * Created by ekalyoncu on 16.10.2022.
 */

@Dao
interface PostDao : BaseDao<PostEntity> {
    @Query("SELECT * FROM ${Constants.TABLE_POST_NAME}")
    fun getAllPosts(): List<PostEntity>

    @Query("DELETE FROM ${Constants.TABLE_POST_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${Constants.TABLE_POST_NAME} WHERE id = :id")
    fun getPostById(id: Long): PostEntity?

}