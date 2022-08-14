package com.daily.app.domain.model

data class Article(
    var description: String?,
    var link: String,
    var published_date: String,
    var source: Source,
    var thumbnail: String?,
    var title: String
)