package com.ekalyoncu.retrofitexample.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private var _favLiveData = MutableLiveData<DataState<List<PostEntity>>>()
    val favLiveData: LiveData<DataState<List<PostEntity>>>
        get() = _favLiveData

    init {
        _favLiveData.value = DataState.Loading()
        _favLiveData.value = DataState.Success(
            postRepository.getFavorites()
        )
    }
}