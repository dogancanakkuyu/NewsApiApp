package com.example.newsapiapp.data

import android.os.Parcelable
import com.google.protobuf.Empty
import kotlinx.parcelize.Parcelize
import java.util.Date

data class News(
    val articles : List<ArticleContent>? = emptyList()
)
@Parcelize
data class ArticleContent(
    val author : String? = "No author specified",
    val title : String = "",
    val url : String = "",
    val urlToImage : String? = "",
    val publishedAt : Date = Date()
) : Parcelable