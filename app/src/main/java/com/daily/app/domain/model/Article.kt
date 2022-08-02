package com.daily.app.domain.model

data class Article(
    val description: String,
    val link: String,
    val published_date: String,
    val source: Source,
    val thumbnail: String,
    val title: String
)