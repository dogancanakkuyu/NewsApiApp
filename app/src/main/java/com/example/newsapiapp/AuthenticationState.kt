package com.example.newsapiapp

sealed class AuthenticationState {
    object Loading : AuthenticationState()
    object Success : AuthenticationState()
    data class Error(val error: String?) : AuthenticationState()
    object Empty : AuthenticationState()
}
