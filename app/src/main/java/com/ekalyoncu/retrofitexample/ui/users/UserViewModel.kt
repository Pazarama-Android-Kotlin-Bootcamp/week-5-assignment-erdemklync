package com.ekalyoncu.retrofitexample.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekalyoncu.retrofitexample.data.model.DataState
import com.ekalyoncu.retrofitexample.data.model.Users
import com.ekalyoncu.retrofitexample.data.repository.UserRepository
import com.ekalyoncu.retrofitexample.ui.posts.viewmodel.PostViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {

    private var _userLiveData = MutableLiveData<DataState<List<Users>?>>()
    val userLiveData: LiveData<DataState<List<Users>?>>
        get() = _userLiveData

    private val _eventStateLiveData = MutableLiveData<PostViewEvent>()
    val eventStateLiveData: LiveData<PostViewEvent>
        get() = _eventStateLiveData

    init {
        getUsers()
    }

    private fun getUsers() {
        _userLiveData.postValue(DataState.Loading())
        userRepository.getUsers().enqueue(
            object : Callback<List<Users>> {
                override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {

                            _userLiveData.postValue(
                                DataState.Success(
                                    it.map { safeUser ->
                                        Users(
                                            id = safeUser.id,
                                            name = safeUser.name,
                                            username = safeUser.username,
                                            phone = safeUser.phone,
                                            email = safeUser.email,
                                            address = null,
                                            company = null,
                                            website = null
                                        )
                                    }
                                )
                            )

                        } ?: kotlin.run {
                            _userLiveData.postValue(DataState.Error("Data Empty"))
                        }
                    } else {
                        _userLiveData.postValue(DataState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    _userLiveData.postValue(DataState.Error(t.message.toString()))
                    _eventStateLiveData.postValue(PostViewEvent.ShowMessage(t.message.toString()))
                }
            }
        )
    }
}