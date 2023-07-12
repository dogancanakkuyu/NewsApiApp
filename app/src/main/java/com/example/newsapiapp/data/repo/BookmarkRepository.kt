package com.example.newsapiapp.data.repo

import com.example.newsapiapp.ResponseState
import com.example.newsapiapp.data.ArticleContent
import com.example.newsapiapp.data.News
import kotlinx.coroutines.flow.Flow
import java.text.FieldPosition

interface BookmarkRepository {
    var responseFlow : Flow<ResponseState>?
    var bookmarkFlow : Flow<List<ArticleContent>>?
    fun addBookmark(articleContent: ArticleContent)
    suspend fun deleteBookmark(articleContent: ArticleContent)
    suspend fun getAllBookmarks() : Flow<List<ArticleContent>>
}