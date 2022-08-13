package com.daily.app.data.remote.dto

data class ResponseDto(
    val articles: List<ArticleDto>,
    val statusCode: Int
)