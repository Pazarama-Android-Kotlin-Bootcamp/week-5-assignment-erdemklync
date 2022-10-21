package com.ekalyoncu.retrofitexample.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ekalyoncu.retrofitexample.utils.Constants

/**
 * Created by ekalyoncu on 16.10.2022.
 */

@Entity(tableName = Constants.TABLE_POST_NAME)
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long? = null,
    @ColumnInfo(name = "postTitle") val postTitle: String?,
    @ColumnInfo(name = "postBody") val postBody: String?,
)
