package com.example.newsapiapp.data

import com.example.newsapiapp.ServiceResponseState
import com.example.newsapiapp.api.NewsApiService
import com.example.newsapiapp.data.repo.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(private val service: NewsApiService) :
    ServiceRepository {
    override suspend fun getArticlesFromRetrofit(
        category: String,
        key: String
    ): Flow<ServiceResponseState> {
        val response = service.getArticlesFromApi(category, key)
        return flow {
            emit(ServiceResponseState.Loading)
            delay(1000L)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ServiceResponseState.Success(it))
                }
            } else {
                emit(ServiceResponseState.Error(response.message()))
            }
        }
    }
}