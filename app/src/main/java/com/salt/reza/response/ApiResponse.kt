package com.salt.reza.response

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
