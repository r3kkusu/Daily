package com.daily.app.data.local.entities

data class ArticleEnt(
    val description: String,
    val link: String,
    val published_date: String,
    val source: SourceEnt,
    val thumbnail: String,
    val title: String
)