package com.daily.app.data.remote.dto

data class ArticleDto(
    val description: String,
    val link: String,
    val published_date: String,
    val source: SourceDto,
    val thumbnail: String,
    val title: String
)