package com.ekalyoncu.week5.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekalyoncu.week5.data.data_source.remote.model.User
import com.ekalyoncu.week5.data.repository.UserRepository
import com.ekalyoncu.week5.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _usersLiveData = MutableLiveData<DataState<List<User>?>>()
    val usersLiveData: LiveData<DataState<List<User>?>>
        get() = _usersLiveData

    init {
        getUsers()
    }

    private fun getUsers() {
        _usersLiveData.postValue(DataState.Loading())
        userRepository.getUsers().enqueue(
            object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _usersLiveData.postValue(
                                DataState.Success(
                                    it.map { safeUser ->
                                        User(
                                            id = safeUser.id,
                                            name = safeUser.name,
                                            username = safeUser.username,
                                            phone = safeUser.phone,
                                            email = safeUser.email,
                                            address = safeUser.address,
                                            company = safeUser.company,
                                            website = safeUser.website
                                        )
                                    }
                                )
                            )
                        } ?: kotlin.run {
                            _usersLiveData.postValue(DataState.Error("Data Empty"))
                        }
                    } else {
                        _usersLiveData.postValue(DataState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _usersLiveData.postValue(DataState.Error(t.message.toString()))
                }
            }
        )
    }
}