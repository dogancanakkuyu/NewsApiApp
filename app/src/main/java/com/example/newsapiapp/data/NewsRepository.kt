package com.example.newsapiapp.data

import com.example.newsapiapp.api.NewsApiService
import com.example.newsapiapp.data.News
import java.io.IOException

class NewsRepository {

    suspend fun getArticles(category : String) : News? {
        val response = NewsApiService.create().getArticlesFromApi(category)
        if (response.isSuccessful){
            return response.body()
        }
        else{
            throw IOException(response.code().toString())
        }
    }
}