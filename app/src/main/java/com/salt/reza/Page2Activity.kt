package com.salt.reza

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salt.reza.adapter.GridAdapter
import com.salt.reza.adapter.HorizontalAdapter
import com.salt.reza.response.ApiResponse
import com.salt.reza.response.Article
import com.salt.reza.response.Source
import com.salt.reza.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Page2Activity : AppCompatActivity() {

    private val horizontalAdapter = HorizontalAdapter()
    private val gridAdapter = GridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page2)

        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontalRecyclerView)
        val gridRecyclerView = findViewById<RecyclerView>(R.id.gridRecyclerView)
        val searchImage = findViewById<ImageView>(R.id.searchImage)

        searchImage.setOnClickListener {
            navigateToPage4()
        }

        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val gridLayoutManager = GridLayoutManager(this, 3)

        horizontalRecyclerView.layoutManager = horizontalLayoutManager
        gridRecyclerView.layoutManager = gridLayoutManager

        horizontalRecyclerView.adapter = horizontalAdapter
        gridRecyclerView.adapter = gridAdapter

        val apiService = RetrofitClient.apiService
        apiService.getTopHeadlines().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val articleList = articles?.map { article ->
                        Article(
                            title = article.title ?: "",
                            description = article.description ?: "",
                            urlToImage = article.urlToImage ?: "",
                            url = article.url ?: "",
                            publishedAt = article.publishedAt,
                            content = article.content ?: ""
                        )
                    }

                    val horizontalData = articleList?.take(5) ?: emptyList()
                    val gridData = articleList?.drop(5)?.take(12) ?: emptyList()

                    horizontalAdapter.setData(horizontalData)
                    gridAdapter.setData(gridData)
                } else {
                    val errorMessage = "Gagal mengambil data dari API"
                    Toast.makeText(this@Page2Activity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val networkErrorMessage = "Terjadi kesalahan koneksi atau jaringan: ${t.message}"
                Toast.makeText(this@Page2Activity, networkErrorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        horizontalAdapter.setOnItemClickListener { article ->
            navigateToPage3(article)
        }

        gridAdapter.setOnItemClickListener { article ->
            navigateToPage3(article)
        }
    }

    private fun navigateToPage3(article: Article) {
        val intent = Intent(this, Page3Activity::class.java)
        intent.putExtra("title", article.title)
        intent.putExtra("description", article.description)
        intent.putExtra("urlToImage", article.urlToImage)
        intent.putExtra("url", article.url)
        intent.putExtra("publishedAt", article.publishedAt)
        intent.putExtra("content", article.content)
        startActivity(intent)
    }

    private fun navigateToPage4() {
        val intent = Intent(this, Page4Activity::class.java)
        startActivity(intent)
    }
}

