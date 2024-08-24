package com.example.jetareader.data

 sealed class Resource<T>(val data: T? = null,val massage:String? = null)
 {
     class Success<T>(data: T) : Resource<T>(data)
     class Error<T>(massage: String?,data: T? = null) : Resource<T>(data,massage)
     class Loading<T>(data: T? = null) : Resource<T>(data)
 }
