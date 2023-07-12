package com.example.newsapiapp.data

import com.example.newsapiapp.LoginState
import com.example.newsapiapp.data.repo.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : LoginRepository {

    override var loginFlow: Flow<LoginState>? = null
    override suspend fun logIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                loginFlow = flow {
                    emit(LoginState.Loading)
                    delay(1500L)
                    if (it.isSuccessful) emit(LoginState.Success)
                    else emit(LoginState.Error(it.exception?.message))
                }
            }
        }
        else {
            loginFlow = flow {
                emit(LoginState.Error("Please fill the blank fields"))
            }
        }
    }

    override fun logOutFromAccount() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}