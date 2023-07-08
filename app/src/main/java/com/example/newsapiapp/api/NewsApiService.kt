package com.example.newsapiapp.api

import com.example.newsapiapp.data.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/top-headlines?country=us")
    suspend fun getArticlesFromApi(
        @Query("category") category: String,
        @Query("apiKey") key: String
    ): Response<News>
}