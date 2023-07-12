package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.LoginState
import com.example.newsapiapp.data.repo.LoginRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginViewModel = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginViewModel: StateFlow<LoginState> = _loginViewModel

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.logIn(email, password)
            loginRepository.loginFlow?.collect{
                _loginViewModel.value = it
            }
        }
    }

    fun logOut() {
        loginRepository.logOutFromAccount()
        _loginViewModel.value = LoginState.Empty
    }

    fun getCurrentUser(): FirebaseUser? {
        return loginRepository.getCurrentUser()
    }
}