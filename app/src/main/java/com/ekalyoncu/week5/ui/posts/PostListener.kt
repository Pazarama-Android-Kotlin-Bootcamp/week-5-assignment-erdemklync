package com.ekalyoncu.week5.ui.posts

import com.ekalyoncu.week5.data.data_source.local.model.PostEntity

interface PostListener {
    fun onPostClick(post: PostEntity)
    fun onFavorite(post: PostEntity){}
}