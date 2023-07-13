package com.example.newsapiapp.data

import com.example.newsapiapp.ResponseState
import com.example.newsapiapp.data.repo.BookmarkRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.FieldPosition
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore, private val userId: String?
) : BookmarkRepository {
    override var responseFlow: Flow<ResponseState>? = null
    override var bookmarkFlow: Flow<List<ArticleContent>>? = null
    override fun addBookmark(articleContent: ArticleContent){
        if (userId != null) {
            val articlesRef = db.collection("users").document(userId)
            articlesRef.get().addOnSuccessListener { document ->
                if (!document.exists()) {
                    articlesRef.set(News())
                } else {
                    articlesRef.update("articles", FieldValue.arrayUnion(articleContent)).addOnCompleteListener {
                        if (it.isSuccessful) println("success")
                        else println("${it.exception?.message}")
                    }


                }.addOnFailureListener {
                }
            }
        }
    }

    override suspend fun deleteBookmark(articleContent: ArticleContent) {
        if (userId != null) {
            val articlesRef = db.collection("users").document(userId)
            articlesRef.update("articles", FieldValue.arrayRemove(articleContent))
                .addOnCompleteListener {
                    if (it.isSuccessful) println("successfully deleted")
                    else println(it.exception?.message)
                }
        }
    }

    override suspend fun getAllBookmarks(): Flow<ResponseState> = flow {
        if (userId != null) {
            val documentRef = db.collection("users").document(userId)
            try {
                val document = documentRef.get().await()
                emit(ResponseState.Loading)
                kotlinx.coroutines.delay(1500L)
                if (!document.exists()) {
                    documentRef.set(News())
                    emit(ResponseState.Success(emptyList()))
                } else {

                    val news = document.toObject(News::class.java)
                    news?.let {
                        val articles = news.articles
                        articles?.let {
                            emit(ResponseState.Success(it))
                        }
                    }
                }
            }catch (e : Exception){
                emit(ResponseState.Error(e.message))
            }

        }
    }.flowOn(Dispatchers.IO)
}