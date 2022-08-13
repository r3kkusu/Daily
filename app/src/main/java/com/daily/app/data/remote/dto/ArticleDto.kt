package com.daily.app.data.remote.dto

data class ArticleDto(
    var description: String,
    var link: String,
    var published_date: String,
    var source: SourceDto,
    var thumbnail: String,
    var title: String
)