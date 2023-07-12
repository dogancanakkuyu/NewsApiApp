package com.example.newsapiapp

sealed class LoginState {
    object Success : LoginState()
    data class Error(val error : String?) : LoginState()
    object Loading : LoginState()
    object Empty : LoginState()
}
