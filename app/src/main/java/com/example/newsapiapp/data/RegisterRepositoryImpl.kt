package com.example.newsapiapp.data

import com.example.newsapiapp.RegisterState
import com.example.newsapiapp.data.repo.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) :
    RegisterRepository {
    override var flow: Flow<RegisterState>? = null

    override suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            flow = flow {
                if (it.isSuccessful){
                    emit(RegisterState.Success("succesfully signed up"))
                }
                else emit(RegisterState.Error(it.exception?.message))
            }
        }

    }
}