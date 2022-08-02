package com.daily.app.domain.repositories

import com.daily.app.common.Resource
import com.daily.app.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface AppRepo {

    fun getTopHeadlines(): Flow<Resource<List<Article>>>

    fun getTopicHeadlines(): Flow<Resource<List<Article>>>

    fun getLocalNews(): Flow<Resource<List<Article>>>

    fun searchNews(): Flow<Resource<List<Article>>>
}