package com.ekalyoncu.week5.ui.posts

import androidx.lifecycle.*
import androidx.navigation.NavController
import com.ekalyoncu.week5.data.data_source.local.model.PostEntity
import com.ekalyoncu.week5.data.data_source.remote.model.Post
import com.ekalyoncu.week5.data.repository.PostRepository
import com.ekalyoncu.week5.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
): ViewModel() {

    private var _postsLiveData = MutableLiveData<DataState<List<PostEntity>?>>(DataState.Loading())
    val postsLiveData: LiveData<DataState<List<PostEntity>?>>
        get() = _postsLiveData

    init {
        getPosts()
    }

    private fun getPosts() {
        _postsLiveData.postValue(DataState.Loading())
        getPostsFromRemote()
        //getPostsFromLocal()
    }

    private fun getPostsFromRemote() {
        postRepository.getPostsFromRemote().enqueue(
            object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            savePosts(it)
                            getPostsFromLocal()
                        }
                        return
                    }
                    _postsLiveData.postValue(DataState.Error(response.message()))
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    _postsLiveData.postValue(DataState.Error(t.message.toString()))
                }
            }
        )
    }

    private fun getPostsFromLocal() {
        postRepository.getPostsFromLocal().let { posts ->
            _postsLiveData.postValue(
                DataState.Success(posts)
            )
        }
    }

    private fun savePosts(posts: List<Post>) {
        postRepository.savePosts(
            posts.map { post ->
                PostEntity(
                    id = post.id ?: 0,
                    title = post.title ?: "",
                    body = post.body ?: "",
                    userId = post.userId ?: 0,
                    isFavorite = false,
                )
            }
        )
    }

    fun onFavorite(post: PostEntity) {
        postRepository.updateFavorite(
            post = post.copy(
                isFavorite = !post.isFavorite
            )
        ).also {
            updatePost(post)
        }
    }

    private fun updatePost(post: PostEntity) {
        val state = _postsLiveData.value

        if(state is DataState.Success) {
            val postList = state.data

            postList?.map {
                if (post.id == it.id){
                    it.copy(
                        isFavorite = !post.isFavorite
                    )
                } else {
                    it
                }
            }.let { updatedPostList ->
                _postsLiveData.postValue(
                    DataState.Success(updatedPostList)
                )
            }
        }
    }
}