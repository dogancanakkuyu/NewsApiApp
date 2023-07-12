package com.example.newsapiapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapiapp.R
import com.example.newsapiapp.data.ArticleContent
import com.example.newsapiapp.data.News

class BookmarkAdapter(private val news: List<ArticleContent>) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bookmark_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: ArticleContent = news[position]
        val imageUrl : String? = article.urlToImage
        holder.bindData(article, imageUrl, article.url)
    }

    fun getArticleContent(position: Int) : ArticleContent {
        return news[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val author: TextView = itemView.findViewById(R.id.author)

        @SuppressLint("SetJavaScriptEnabled")
        fun bindData(article: ArticleContent, imageUrl: String?, url: String) {

            if (imageUrl != null) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .override(80, 80)
                    .transform(CenterCrop(), RoundedCorners(15))
                    .into(image)
            }

            title.text = article.title
            author.text = article.author
        }
    }

}