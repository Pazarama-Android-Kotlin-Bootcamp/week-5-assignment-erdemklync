package com.ekalyoncu.week5.ui.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekalyoncu.week5.R
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.databinding.ItemPostBinding
import com.ekalyoncu.week5.util.generateRandomColor

class PostsAdapter(
    private val listener: PostListener,
) : ListAdapter<PostEntity, PostsAdapter.PostViewHolder>(PostsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PostViewHolder(
        private val binding: ItemPostBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostEntity, listener: PostListener) {
            with(binding) {
                root.setOnClickListener {
                    listener.onPostClick(post)
                }

                Glide
                    .with(binding.root)
                    .load("https://picsum.photos/id/${100 + post.id}/300")
                    .centerCrop()
                    .into(imagePost)

                textPostTitle.text = post.title
                textPostContent.text = post.body

                iconFavorite.setColorFilter(getFavoriteIconColor(post))
                iconFavorite.setOnClickListener {
                    listener.onFavorite(post)
                }
            }
        }

        private fun getFavoriteIconColor(post: PostEntity) : Int {
            val colorRed = ContextCompat.getColor(itemView.context, R.color.red)
            val colorGray = ContextCompat.getColor(itemView.context, R.color.gray)

            return if(post.isFavorite) colorRed else colorGray
        }
    }

    class PostsDiffUtil : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem == newItem
        }
    }
}