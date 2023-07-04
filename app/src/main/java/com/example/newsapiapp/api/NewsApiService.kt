package com.example.newsapiapp.api

import com.example.newsapiapp.data.News
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/top-headlines?country=us&apiKey=bac6e6db44314dd49ead103a224fc0f0")
    suspend fun getArticlesFromApi(@Query("category") category : String) : Response<News>

    companion object {
        private val BASE_URL : String = "https://newsapi.org"

        fun create() : NewsApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}