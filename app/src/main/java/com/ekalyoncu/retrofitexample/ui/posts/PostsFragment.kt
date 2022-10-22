package com.ekalyoncu.retrofitexample.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.model.PostDTO
import com.ekalyoncu.retrofitexample.ui.loadingprogress.LoadingProgressBar
import com.ekalyoncu.retrofitexample.ui.posts.adapter.OnPostClickListener
import com.ekalyoncu.retrofitexample.ui.posts.adapter.PostsAdapter
import com.ekalyoncu.retrofitexample.ui.posts.viewmodel.PostViewEvent
import com.ekalyoncu.retrofitexample.ui.posts.viewmodel.PostsViewModel
import com.google.android.material.snackbar.Snackbar
import com.ekalyoncu.retrofitexample.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment() {

    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentPostsBinding
    private val viewModel by viewModels<PostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = LoadingProgressBar(requireContext())

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.postLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        binding.rvPostsList.adapter = PostsAdapter(
                            object : OnPostClickListener {
                                override fun onPostClick(post: PostDTO) {
                                    TODO("Not yet implemented")
                                }
                            }
                        ).apply {
                            submitList(safeData)
                        }
                    } ?: run {
                        Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
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

        viewModel.eventStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is PostViewEvent.ShowMessage -> {}
                is PostViewEvent.NavigateToDetail -> {}
            }
        }
    }

}