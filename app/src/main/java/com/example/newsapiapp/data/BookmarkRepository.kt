package com.example.newsapiapp.data

import android.content.Context
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class BookmarkRepository {


    private val db = Firebase.firestore

    fun addNewsToBookmarks(articleContent: ArticleContent, userId : String) {
        db.collection("users").document(userId).collection("news")
            .document().set(articleContent).addOnCompleteListener {
                if (it.isSuccessful) println("succesfully added")
                else println(it.exception)
            }
    }

}