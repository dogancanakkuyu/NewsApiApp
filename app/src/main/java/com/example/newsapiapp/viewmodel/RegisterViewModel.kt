package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.RegisterState
import com.example.newsapiapp.data.repo.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    ViewModel() {
    private val _registerFlow = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val registerFlow: StateFlow<RegisterState> = _registerFlow

    suspend fun registerWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            registerRepository.registerUserWithEmailAndPassword(email, password)
            registerRepository.flow?.let { flow ->
                flow.collect{
                    _registerFlow.value = it
                }
            }
        }
    }
}