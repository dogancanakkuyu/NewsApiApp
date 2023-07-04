package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapiapp.data.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth

class AuthenticationViewModel : ViewModel() {
    private val authenticationRepository : AuthenticationRepository = AuthenticationRepository()

    fun getUser() : FirebaseAuth {
        return authenticationRepository.getAuth()
    }

    fun getCurrentUserId() : String? {
        return authenticationRepository.getCurrentUserId()
    }

    fun registerUser(email : String, password : String) {
        authenticationRepository.signUp(email,password)
    }

    fun logInUser(email: String,password: String) {
        authenticationRepository.logIn(email,password)
    }

    fun isUserExists() : Boolean {
        return authenticationRepository.isUserExists()
    }

    fun logOutFromAccount() {
        authenticationRepository.logOutFromAccount()
    }
}