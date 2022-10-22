package com.ekalyoncu.retrofitexample.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ekalyoncu.retrofitexample.R
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.model.PostDTO
import com.ekalyoncu.retrofitexample.databinding.FragmentFavoritesBinding
import com.ekalyoncu.retrofitexample.databinding.FragmentPostDetailBinding
import com.ekalyoncu.retrofitexample.ui.posts.adapter.OnPostClickListener
import com.ekalyoncu.retrofitexample.ui.posts.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is DataState.Loading -> {}
                is DataState.Error -> {}
                is DataState.Success -> {
                    binding.rvPostsList.adapter = PostsAdapter(
                        object : OnPostClickListener {
                            override fun onPostClick(post: PostDTO) {
                                val bundle = bundleOf("postId" to post.id)
                                findNavController().navigate(R.id.postDetailFragment, bundle)
                            }
                        }
                    ).apply {
                        submitList(
                            it.data.map {
                                PostDTO(
                                    userId = it.id?.toInt(),
                                    id = it.id?.toLong(),
                                    body = it.postBody,
                                    title = it.postTitle,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}