package com.daily.app.common.mapper

import com.daily.app.data.local.entities.ArticleEnt
import com.daily.app.data.local.entities.enums.Categories
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

fun ArticleEnt.toArticle() : Article {
    return Article(
        description = description,
        link = link,
        published_date = published_date,
        source = source.toSource(),
        thumbnail = thumbnail,
        title = title
    )
}

fun ArticleDto.toArticleEnt(categories: Categories) : ArticleEnt {
    return ArticleEnt(
        article_id = 0,
        description = description,
        link = link,
        published_date = published_date,
        source = source.toSourceEnt(),
        thumbnail = thumbnail,
        title = title,
        category = categories.name
    )
}