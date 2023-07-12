package com.example.newsapiapp.data.repo

import com.example.newsapiapp.LoginState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    var loginFlow : Flow<LoginState>?
    fun logOutFromAccount()
    suspend fun logIn(email: String, password: String)
    fun getCurrentUser() : FirebaseUser?


}