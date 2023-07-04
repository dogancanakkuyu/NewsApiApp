package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapiapp.data.ArticleContent
import com.example.newsapiapp.data.BookmarkRepository

class BookmarkViewModel : ViewModel(){
    private val bookmarkRepository : BookmarkRepository = BookmarkRepository()

    fun addBookmark(articleContent: ArticleContent,documentId: String) {
        bookmarkRepository.addNewsToBookmarks(articleContent,documentId)
    }
}