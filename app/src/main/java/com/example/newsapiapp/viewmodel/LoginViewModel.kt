package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.LoginState
import com.example.newsapiapp.data.repo.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository.LogInRepository
) : ViewModel() {

    private val _loginViewModel = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginViewModel : StateFlow<LoginState> = _loginViewModel

    fun logIn(email : String, password : String) {
        viewModelScope.launch {
            val flow = repository.logIn(email, password)
            flow.collect{
                _loginViewModel.value = it
            }
        }
    }

    fun logOut() {
        repository.logOutFromAccount()
        _loginViewModel.value = LoginState.Empty
    }

    fun getCurrentUser() : FirebaseUser? {
        return repository.getCurrentUser()
    }
}