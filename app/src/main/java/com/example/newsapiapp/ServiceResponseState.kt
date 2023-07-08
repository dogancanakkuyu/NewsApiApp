package com.example.newsapiapp

import com.example.newsapiapp.data.News
import java.lang.Error

sealed class ServiceResponseState{
    data class Success(val body : News) : ServiceResponseState()
    data class Error(val error: String) : ServiceResponseState()
    object Loading : ServiceResponseState()
    object Empty : ServiceResponseState()
}