package com.example.newsapiapp

sealed class RegisterState {
    data class Success(val msg: String) : RegisterState()
    data class Error(val error: String?) : RegisterState()
    object Empty : RegisterState()
}
