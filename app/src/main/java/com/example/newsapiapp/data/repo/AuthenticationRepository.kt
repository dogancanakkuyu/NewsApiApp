package com.example.newsapiapp.data.repo

import com.example.newsapiapp.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    var authFlow : Flow<AuthenticationState>?
    fun sendPasswordResetEmail(email: String)
}