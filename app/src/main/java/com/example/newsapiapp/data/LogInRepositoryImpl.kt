package com.example.newsapiapp.data

import android.util.Log
import com.example.newsapiapp.LoginState
import com.example.newsapiapp.data.repo.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository.LogInRepository {

    override suspend fun logIn(email: String, password: String): Flow<LoginState> {
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            flow {
                emit(LoginState.Loading)
                delay(1500L)
                val result = auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) emit(LoginState.Success)
                else emit(LoginState.Error("Wrong email or password"))
            }
        } else {
            flow {
                emit(LoginState.Error(""))
            }
        }
    }

    override fun logOutFromAccount() {
        auth.signOut()
    }

    override fun getCurrentUser() : FirebaseUser?{
        return auth.currentUser
    }

}