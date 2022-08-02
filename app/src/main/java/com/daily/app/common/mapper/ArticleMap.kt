package com.daily.app.common.mapper

import com.daily.app.data.remote.dto.ArticleDto
import com.daily.app.domain.model.Article

fun ArticleDto.toArticle() : Article {
    return Article(
        description = description,
        link = link,
        published_date = published_date,
        source = source.toSource(),
        thumbnail = thumbnail,
        title = title
    )
}