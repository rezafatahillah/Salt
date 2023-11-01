package com.salt.reza.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.salt.reza.response.ApiResponse


interface ApiService {
    @GET("top-headlines?country=us")
    fun getTopHeadlines(): Call<ApiResponse>

    @GET("everything")
    fun searchNews(
        @Query("q") query: String,
        @Query("from") fromDate: String? = null,
        @Query("sortBy") sortBy: String? = null
    ): Call<ApiResponse>
}