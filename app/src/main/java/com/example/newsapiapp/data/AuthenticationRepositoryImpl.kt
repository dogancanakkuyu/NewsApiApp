package com.example.newsapiapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepositoryImpl {
    private var firebaseAuth : FirebaseAuth = Firebase.auth
    private var isUserExists : Boolean = false
    fun getAuth(): FirebaseAuth {
        return firebaseAuth
    }

    fun getCurrentUserId() : String? {
        return firebaseAuth.uid
    }

    fun signUp(email : String, password : String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                println("successfully signed up")
            }
            else{
                println("failed while signing up")
            }
        }
    }
    fun logOutFromAccount() {
        firebaseAuth.signOut()
    }

    fun logIn(email: String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                isUserExists = it.isSuccessful
            }
        }
    }

    fun isUserExists() : Boolean{
        return isUserExists
    }
}