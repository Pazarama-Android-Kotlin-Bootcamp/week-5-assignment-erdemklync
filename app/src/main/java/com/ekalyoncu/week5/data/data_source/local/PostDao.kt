package com.ekalyoncu.week5.data.data_source.local

import androidx.room.*
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.data.data_source.remote.model.Post
import com.ekalyoncu.week5.util.Constants

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostEntity)

    @Update
    fun update(post: PostEntity)

    @Delete
    fun delete(post: PostEntity)

    @Query("SELECT * FROM ${Constants.TABLE_POST_NAME}")
    fun getPosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(posts: List<PostEntity>)

    @Query("SELECT * FROM ${Constants.TABLE_POST_NAME} WHERE isFavorite = 1")
    fun getFavoritePosts(): List<PostEntity>

    @Query("DELETE FROM ${Constants.TABLE_POST_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${Constants.TABLE_POST_NAME} WHERE id = :id")
    fun getPostById(id: Long): PostEntity?

}