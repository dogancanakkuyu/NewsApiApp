package com.example.newsapiapp.data.repo

import com.example.newsapiapp.RegisterState
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    val flow: Flow<RegisterState>?
    suspend fun registerUserWithEmailAndPassword(email: String, password: String)
}