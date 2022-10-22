package com.ekalyoncu.retrofitexample.ui.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {

    private val viewModel: PostDetailViewModel by viewModels()

    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    binding.post = it.data
                }
                is DataState.Error -> {}
                is DataState.Loading -> {}
            }
        }
    }
}