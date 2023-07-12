package com.example.newsapiapp

sealed class ResponseState {
    object Success : ResponseState()
    data class Error(val msg : String?) : ResponseState()
    object Loading : ResponseState()
    object Empty : ResponseState()
}
