package com.ekalyoncu.retrofitexample.ui.posts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekalyoncu.retrofitexample.data.local.database.entity.PostEntity
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.model.Post
import com.ekalyoncu.retrofitexample.data.model.PostDTO
import com.ekalyoncu.retrofitexample.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private var _postLiveData = MutableLiveData<DataState<List<PostDTO>?>>()
    val postLiveData: LiveData<DataState<List<PostDTO>?>>
        get() = _postLiveData

    private val _eventStateLiveData = MutableLiveData<PostViewEvent>()
    val eventStateLiveData: LiveData<PostViewEvent>
        get() = _eventStateLiveData

    init {
        getPosts()
    }

    private fun getPosts() {
        _postLiveData.postValue(DataState.Loading())
        postRepository.getPosts().enqueue(
            object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _postLiveData.postValue(
                                DataState.Success(
                                    it.map { safePost ->
                                        PostDTO(
                                            id = safePost.id?.toLong(),
                                            title = safePost.title,
                                            body = safePost.body,
                                            userId = safePost.userId,
                                        )
                                    }
                                )
                            )
                        } ?: kotlin.run {
                            _postLiveData.postValue(DataState.Error("Data Empty"))
                        }
                    } else {
                        _postLiveData.postValue(DataState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    _postLiveData.postValue(DataState.Error(t.message.toString()))
                    _eventStateLiveData.postValue(PostViewEvent.ShowMessage(t.message.toString()))
                }
            }
        )
    }
}

sealed class PostViewEvent {
    object NavigateToDetail : PostViewEvent()
    class ShowMessage(val message: String?) : PostViewEvent()
}