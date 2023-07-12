package com.example.newsapiapp.data

import com.example.newsapiapp.AuthenticationState
import com.example.newsapiapp.data.repo.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) :
    AuthenticationRepository {

    override var authFlow: Flow<AuthenticationState>? = null

    override fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            authFlow = flow {
                emit(AuthenticationState.Loading)
                delay(1000L)
                if (it.isSuccessful) emit(AuthenticationState.Success)
                else emit(AuthenticationState.Error(it.exception?.message))
            }
        }
    }
}