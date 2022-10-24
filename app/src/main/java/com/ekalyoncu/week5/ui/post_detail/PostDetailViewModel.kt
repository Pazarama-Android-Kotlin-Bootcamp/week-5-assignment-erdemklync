package com.ekalyoncu.week5.ui.post_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ekalyoncu.week5.data.data_source.remote.model.Post
import com.ekalyoncu.week5.data.repository.PostRepository
import com.ekalyoncu.week5.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _detailLiveData = MutableLiveData<DataState<Post>>()
    val detailLiveData: LiveData<DataState<Post>>
        get() = _detailLiveData


    init {
        savedStateHandle.get<Int>("postId")?.let { id ->
            getPostById(id)
        }
    }

    private fun getPostById(id: Int) {
        postRepository.getPostById(id).enqueue(
            object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _detailLiveData.postValue(
                                DataState.Success(
                                    Post(
                                        id = it.id,
                                        title = it.title,
                                        body = it.body,
                                        userId = it.userId,
                                    )
                                )
                            )
                        } ?: kotlin.run {
                            _detailLiveData.postValue(DataState.Error("Data Empty"))
                        }
                    } else {
                        _detailLiveData.postValue(DataState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    _detailLiveData.postValue(DataState.Error(t.message.toString()))
                }
            }
        )
    }
}