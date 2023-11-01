package com.salt.reza

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.salt.reza.tools.DateUtils

class Page3Activity : AppCompatActivity() {
    private var isContentVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3)

        val newsImage = findViewById<ImageView>(R.id.newsImage)
        val newsTitle = findViewById<TextView>(R.id.newsTitle)
        val newsDescription = findViewById<TextView>(R.id.newsDescription)
        val newsPublishedDate = findViewById<TextView>(R.id.newsPublishedDate)
        val newsContent = findViewById<TextView>(R.id.newsContent)
        val readMoreButton = findViewById<Button>(R.id.readMoreButton)

        val intent = intent
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val urlToImage = intent.getStringExtra("urlToImage")
        val url = intent.getStringExtra("url")
        val publishedAt = intent.getStringExtra("publishedAt")
        val content = intent.getStringExtra("content")

        newsTitle.text = title
        newsDescription.text = description
        val formattedDate = publishedAt?.let { DateUtils.convertDateFormat(it) } ?: ""
        newsPublishedDate.text = formattedDate

        if (urlToImage != null) {
            Glide.with(this)
                .load(urlToImage)
                .into(newsImage)
        }

        readMoreButton.setOnClickListener {
            if (!content.isNullOrBlank()) {
                isContentVisible = !isContentVisible
                if (isContentVisible) {
                    newsDescription.visibility = TextView.GONE
                    newsContent.text = content
                    newsContent.visibility = TextView.VISIBLE
                    readMoreButton.text = "Read Less"
                } else {
                    newsContent.visibility = TextView.GONE
                    newsDescription.visibility = TextView.VISIBLE
                    readMoreButton.text = "Read More"
                }
            } else if (!url.isNullOrBlank()) {
                val webpage = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}