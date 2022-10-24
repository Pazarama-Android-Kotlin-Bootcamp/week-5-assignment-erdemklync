package com.ekalyoncu.week5.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.data.repository.PostRepository
import com.ekalyoncu.week5.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val postRepository: PostRepository,
): ViewModel() {

    private var _postsLiveData = MutableLiveData<DataState<List<PostEntity>?>>(DataState.Loading())
    val postsLiveData: LiveData<DataState<List<PostEntity>?>>
        get() = _postsLiveData

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch {
        postRepository.getFavoritePosts().let { posts ->
            _postsLiveData.postValue(
                DataState.Success(posts)
            )
        }
    }
}