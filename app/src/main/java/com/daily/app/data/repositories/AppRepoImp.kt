package com.daily.app.data.repositories

import com.daily.app.common.Resource
import com.daily.app.data.local.entities.ArticleEnt
import com.daily.app.data.remote.AppAPI
import com.daily.app.data.remote.dto.ResponseDto
import com.daily.app.domain.model.Article
import com.daily.app.domain.repositories.AppRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImp @Inject constructor(
    private val appRepo: AppRepo
) : AppRepo{
    override fun getTopHeadlines(): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }

    override fun getTopicHeadlines(): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }

    override fun getLocalNews(): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }

    override fun searchNews(): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }
}