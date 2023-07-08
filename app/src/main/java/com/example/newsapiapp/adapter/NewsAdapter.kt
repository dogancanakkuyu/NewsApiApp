package com.example.newsapiapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapiapp.view.MainFragmentDirections
import com.example.newsapiapp.data.ArticleContent
import com.example.newsapiapp.R

class NewsAdapter(private val articles: List<ArticleContent>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        val imageUrl : String? = articles[position].urlToImage
        val url : String = articles[position].url
        holder.bindData(article,imageUrl,url)
        holder.itemView.setOnClickListener {
            println(article)
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(article)
            it.findNavController().navigate(action)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image : ImageView = itemView.findViewById(R.id.image)
        private val title : TextView = itemView.findViewById(R.id.title)
        private val author : TextView = itemView.findViewById(R.id.author)

        @SuppressLint("SetJavaScriptEnabled")
        fun bindData(article : ArticleContent, imageUrl : String?, url : String){

            if(imageUrl != null){
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .override(80,80)
                    .transform(CenterCrop(),RoundedCorners(15))
                    .into(image)
            }

            title.text = article.title
            author.text = article.author
        }
    }

}