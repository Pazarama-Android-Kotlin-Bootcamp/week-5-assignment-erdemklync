package com.ekalyoncu.week5.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ekalyoncu.week5.R
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.databinding.FragmentFavoritesBinding
import com.ekalyoncu.week5.databinding.FragmentPostDetailBinding
import com.ekalyoncu.week5.databinding.FragmentPostsBinding
import com.ekalyoncu.week5.ui.posts.PostListener
import com.ekalyoncu.week5.ui.posts.PostsAdapter
import com.ekalyoncu.week5.util.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), PostListener {

    private val viewModel: FavoritesViewModel by viewModels()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter: PostsAdapter = PostsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.recyclerViewPost.adapter = postsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    //loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        postsAdapter.submitList(safeData)
                    }
                }
                is DataState.Error -> {
                    //loadingProgressBar.hide()
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    //loadingProgressBar.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPostClick(post: PostEntity) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToPostDetailFragment(post.id)
        findNavController().navigate(action)
    }
}