package com.example.newsapiapp

import com.example.newsapiapp.data.ArticleContent

sealed class ResponseState {
    data class Success(val articles : List<ArticleContent>) : ResponseState()
    data class Error(val msg : String?) : ResponseState()
    object Loading : ResponseState()
    object Empty : ResponseState()
}
