package com.ekalyoncu.retrofitexample.data.model

/**
 * Created by ekalyoncu on 16.10.2022.
 */

sealed class DataState<T> {
    class Success<T>(val data: T) : DataState<T>()
    class Error<T>(val message: String) : DataState<T>()
    class Loading<T> : DataState<T>()
}