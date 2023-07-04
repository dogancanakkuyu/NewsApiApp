package com.example.newsapiapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.R

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