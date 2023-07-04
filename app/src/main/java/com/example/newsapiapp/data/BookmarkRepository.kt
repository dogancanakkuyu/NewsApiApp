package com.example.newsapiapp.data

import com.example.newsapiapp.data.ArticleContent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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