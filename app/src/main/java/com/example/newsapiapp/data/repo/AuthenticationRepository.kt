package com.example.newsapiapp.data.repo

import com.example.newsapiapp.LoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    interface AuthorizationRepository {
        fun getFirebaseAuth(): FirebaseAuth
        fun getCurrentUserId(): String
    }

    interface LogInRepository {
        fun logOutFromAccount()
        suspend fun logIn(email: String, password: String) : Flow<LoginState>
        fun getCurrentUser() : FirebaseUser?
    }

    interface RegisterRepository {
        fun registerUser(email: String, password: String)
    }

}