package com.androiddevs.mvvmnewsapp.util


//Class recommended from Google to wrap Network response
//it takes data and message
//sealed is like abstract but the child classes are allowed to inherit from parent class
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}