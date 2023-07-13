package com.example.newsapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.ResponseState
import com.example.newsapiapp.data.ArticleContent
import com.example.newsapiapp.data.News
import com.example.newsapiapp.data.repo.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) : ViewModel(){
    private val _responseFlow = MutableStateFlow<ResponseState>(ResponseState.Empty)
    val responseFlow : StateFlow<ResponseState> = _responseFlow

    private val _bookmarkFlow = MutableStateFlow<ResponseState>(ResponseState.Empty)
    val bookmarkFlow : StateFlow<ResponseState> = _bookmarkFlow
    fun addBookmark(articleContent: ArticleContent) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(articleContent)
            bookmarkRepository.responseFlow?.collect{
                _responseFlow.value = it
            }
        }
    }

    fun deleteBookmark(articleContent: ArticleContent) {
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(articleContent)
        }
    }

    fun getAllBookmarks() {
        viewModelScope.launch{
            bookmarkRepository.getAllBookmarks().collect{
                _bookmarkFlow.value = it
            }
        }
    }
}