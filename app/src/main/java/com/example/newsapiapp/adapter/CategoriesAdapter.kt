package com.example.newsapiapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.MainFragment
import com.example.newsapiapp.R
import com.google.android.material.button.MaterialButton

class CategoriesAdapter(private val categories: List<String>, private val listener: ClickListener) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    interface ClickListener {
        fun onClickListener(item: Int)
        fun getSelectedItem(): Int
        fun setSelectedItem(item: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_categories_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(categories[position], position)
        holder.itemView.setOnClickListener {
            listener.setSelectedItem(position)
            listener.onClickListener(position)
            notifyDataSetChanged()
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eachCategory: TextView = itemView.findViewById(R.id.category)
        fun bindData(category: String, position: Int) {
            if(position == listener.getSelectedItem()) eachCategory.setBackgroundResource(R.drawable.selected_item)
            else eachCategory.setBackgroundResource(R.drawable.unselected_item)
            eachCategory.text = category.replaceFirstChar {
                it.uppercase()
            }
        }
    }


}