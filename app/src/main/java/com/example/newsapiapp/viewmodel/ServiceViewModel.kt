package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.ServiceResponseState
import com.example.newsapiapp.data.repo.ServiceRepository
import com.example.newsapiapp.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(private val serviceRepository: ServiceRepository) :
    ViewModel() {
    private val _serviceFlow = MutableStateFlow<ServiceResponseState>(ServiceResponseState.Empty)
    val serviceFlow: StateFlow<ServiceResponseState> = _serviceFlow

    fun getArticles(category: String) {
        viewModelScope.launch {
            val flow = serviceRepository.getArticlesFromRetrofit(category, Constant.API_KEY)
            flow.collect {
                _serviceFlow.value = it
            }
        }
    }
}