package com.ekalyoncu.retrofitexample.ui.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.model.Post
import com.ekalyoncu.retrofitexample.data.model.PostDTO
import com.ekalyoncu.retrofitexample.data.repository.PostRepository
import com.ekalyoncu.retrofitexample.ui.posts.viewmodel.PostViewEvent
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
        savedStateHandle.get<Long>("postId")?.let { id ->
            getPostById(id)
        }
    }

    private fun getPostById(id: Long) {
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