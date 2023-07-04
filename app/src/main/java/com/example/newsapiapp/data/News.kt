package com.example.newsapiapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

data class News(
    val articles : List<ArticleContent>? = null
)
@Parcelize
data class ArticleContent(
    val author : String? = "",
    val title : String,
    val url : String,
    val urlToImage : String? = "",
    val publishedAt : Date
) : Parcelable