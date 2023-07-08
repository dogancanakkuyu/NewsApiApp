package com.example.newsapiapp.data.repo

import com.example.newsapiapp.ServiceResponseState
import com.example.newsapiapp.data.News
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun getArticlesFromRetrofit(category: String, key: String): Flow<ServiceResponseState>
}