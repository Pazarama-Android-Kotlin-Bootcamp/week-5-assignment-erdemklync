package com.ekalyoncu.week5.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.databinding.FragmentPostsBinding
import com.ekalyoncu.week5.ui.favorites.FavoritesFragmentDirections
import com.ekalyoncu.week5.ui.loading.LoadingProgressBar
import com.ekalyoncu.week5.util.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(), PostListener {

    private val viewModel: PostsViewModel by viewModels()

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    lateinit var loadingProgressBar: LoadingProgressBar
    private val postsAdapter: PostsAdapter = PostsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        binding.recyclerViewPost.adapter = postsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        postsAdapter.submitList(safeData)
                    }
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }
        }

        binding.fabFavorites.setOnClickListener {
            val action = PostsFragmentDirections.actionPostsFragmentToFavoritesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPostClick(post: PostEntity) {
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(post.id)
        findNavController().navigate(action)
    }

    override fun onFavorite(post: PostEntity) {
        super.onFavorite(post)
        viewModel.onFavorite(post)
    }
}