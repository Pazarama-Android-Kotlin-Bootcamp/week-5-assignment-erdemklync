package com.ekalyoncu.week5.ui.post_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ekalyoncu.week5.R
import com.ekalyoncu.week5.databinding.FragmentPostDetailBinding
import com.ekalyoncu.week5.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private val viewModel: PostDetailViewModel by viewModels()

    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)

        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    with(binding) {
                        it.data.let { post ->
                            Glide
                                .with(binding.root)
                                .load("https://picsum.photos/id/${100 + (post.id ?: 0)}/300")
                                .centerCrop()
                                .into(imagePost)
                            textPostTitle.text = post.title
                            textPostBody.text = post.body
                        }
                    }
                }
                is DataState.Error -> {
                    Log.e("ERROR:", it.message)
                }
                is DataState.Loading -> {}
            }
        }
    }
}