package com.salt.reza

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salt.reza.adapter.SearchAdapter
import com.salt.reza.response.ApiResponse
import com.salt.reza.response.Article
import com.salt.reza.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Page4Activity : AppCompatActivity() {
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page4)

        val beforeDateSpinner = findViewById<Spinner>(R.id.beforeDateSpinner)
        val sortBySpinner = findViewById<Spinner>(R.id.sortBySpinner)

        val beforeDateAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.before_date_options,
            android.R.layout.simple_spinner_item
        )
        beforeDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        beforeDateSpinner.adapter = beforeDateAdapter

        val sortByAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_by_options,
            android.R.layout.simple_spinner_item
        )
        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortBySpinner.adapter = sortByAdapter

        val beforeDateSelectedItem = beforeDateSpinner.selectedItem.toString()
        val sortBySelectedItem = sortBySpinner.selectedItem.toString()

        searchRecyclerView = findViewById(R.id.searchRecyclerView)
        searchAdapter = SearchAdapter()
        val layoutManager = LinearLayoutManager(this)
        searchRecyclerView.layoutManager = layoutManager
        searchRecyclerView.adapter = searchAdapter

        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchNews(query,beforeDateSelectedItem, sortBySelectedItem)
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                searchNews(query,beforeDateSelectedItem, sortBySelectedItem)
            }
        })

    }

    private fun searchNews(query: String, fromDate: String, sortBy: String) {

        val apiService = RetrofitClient.apiService
        apiService.searchNews(query, fromDate, sortBy).enqueue(object : Callback<ApiResponse> {
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
                    searchAdapter.setData(articleList ?: emptyList())
                }else {
                    val errorMessage = "Gagal mengambil data dari API"
                    Toast.makeText(this@Page4Activity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val networkErrorMessage = "Terjadi kesalahan koneksi atau jaringan: ${t.message}"
                Toast.makeText(this@Page4Activity, networkErrorMessage, Toast.LENGTH_SHORT).show()
            }
        })
        searchAdapter.setOnItemClickListener { article ->
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
}
