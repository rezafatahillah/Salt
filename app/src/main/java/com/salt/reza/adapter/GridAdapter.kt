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

class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private var data: List<Article> = emptyList()
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setData(newData: List<Article>) {
        data = newData
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

            Glide.with(itemView)
                .load(article.urlToImage)
                .into(imageView)

            itemView.setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}