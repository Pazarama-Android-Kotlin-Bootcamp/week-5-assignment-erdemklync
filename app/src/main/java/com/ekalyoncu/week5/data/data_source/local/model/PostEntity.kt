package com.ekalyoncu.week5.data.data_source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val body: String,
    val title: String,
    val isFavorite: Boolean,
)