package com.example.newsapiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.data.News
import com.example.newsapiapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository()
    private val _articles = MutableLiveData<News?>()
    val articles: LiveData<News?> get() = _articles
    fun getArticlesFromApi(category: String) {
        var news: News? = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                news = newsRepository.getArticles(category)

            } catch (e: IOException) {
                println(e.message + "problem at NewsViewModel")
            }
        }.invokeOnCompletion {

            if (it == null) _articles.postValue(news)
            else println(it.message + "problem at ViewNodel")

        }
    }

}