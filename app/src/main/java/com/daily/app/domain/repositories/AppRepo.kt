package com.daily.app.domain.repositories

import com.daily.app.common.Resource
import com.daily.app.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface AppRepo {

    fun getTopHeadlines(country: String, lang: String, limit: String): Flow<Resource<List<Article>>>

    fun getTopicHeadlines(topic: String, country: String, lang: String, limit: String): Flow<Resource<List<Article>>>

    fun getLocalNews(geo: String, country: String, lang: String, limit: String): Flow<Resource<List<Article>>>

    fun searchNews(term: String, country: String, lang: String, limit: String): Flow<Resource<List<Article>>>
}