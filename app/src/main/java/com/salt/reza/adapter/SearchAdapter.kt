package com.salt.reza.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salt.reza.R
import com.salt.reza.response.Article
import com.salt.reza.tools.DateUtils

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val searchResults = mutableListOf<Article>()
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setData(data: List<Article>) {
        searchResults.clear()
        searchResults.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(article: Article) {
            titleTextView.text = article.title
            val formattedDate = article.publishedAt?.let { DateUtils.convertDateFormat(it) } ?: ""
            dateTextView.text = formattedDate

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(imageView)
        }

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(searchResults[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = searchResults[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }
}