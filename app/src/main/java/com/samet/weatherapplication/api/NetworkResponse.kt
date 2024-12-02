package com.samet.weatherapplication.api

sealed class NetworkResponse<out T> {
    data class Succes<out T>(val data : T) : NetworkResponse<T>()
    data class Error(val message:String):NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}