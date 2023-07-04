package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapiapp.data.AuthenticationRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel : ViewModel() {
    private val authenticationRepositoryImpl : AuthenticationRepositoryImpl = AuthenticationRepositoryImpl()

    fun getUser() : FirebaseAuth {
        return authenticationRepositoryImpl.getAuth()
    }

    fun getCurrentUserId() : String? {
        return authenticationRepositoryImpl.getCurrentUserId()
    }

    fun registerUser(email : String, password : String) {
        authenticationRepositoryImpl.signUp(email,password)
    }

    fun logInUser(email: String,password: String) {
        authenticationRepositoryImpl.logIn(email,password)
    }

    fun isUserExists() : Boolean {
        return authenticationRepositoryImpl.isUserExists()
    }

    fun logOutFromAccount() {
        authenticationRepositoryImpl.logOutFromAccount()
    }
}