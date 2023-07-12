package com.example.newsapiapp.data

import com.example.newsapiapp.ResponseState
import com.example.newsapiapp.data.repo.BookmarkRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.FieldPosition
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore, private val userId: String?
) : BookmarkRepository {
    override var responseFlow: Flow<ResponseState>? = null
    override var bookmarkFlow: Flow<List<ArticleContent>>? = null
    override fun addBookmark(articleContent: ArticleContent) {
        if (userId != null) {
            val articlesRef = db.collection("users").document(userId)
            articlesRef.get().addOnSuccessListener { document ->
                if (!document.exists()) {
                    articlesRef.set(News())
                } else {
                    articlesRef.update("articles", FieldValue.arrayUnion(articleContent))
                        .addOnCompleteListener {
                            responseFlow = flow {
                                if (it.isSuccessful) emit(ResponseState.Success)
                                else emit(ResponseState.Error(it.exception?.message))
                            }
                        }
                }
            }.addOnFailureListener {
                responseFlow = flow {
                    emit(ResponseState.Error(it.message))
                }
            }
        }
    }

    override suspend fun deleteBookmark(articleContent: ArticleContent) {
        if (userId != null) {
            val articlesRef = db.collection("users").document(userId)
            articlesRef.update("articles",FieldValue.arrayRemove(articleContent)).addOnCompleteListener {
                if (it.isSuccessful) println("successfully deleted")
                else println(it.exception?.message)
            }
        }
    }

    override suspend fun getAllBookmarks() : Flow<List<ArticleContent>> = flow{
        if (userId != null) {
            val documentRef = db.collection("users").document(userId)
            val document = documentRef.get().await()
            if (!document.exists()) {
                documentRef.set(News())
                emit(emptyList<ArticleContent>())
            }else{

                val news = document.toObject(News::class.java)
                news?.let {
                    val articles = news.articles
                    articles?.let {
                        emit(it)
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}