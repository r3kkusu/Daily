package com.daily.app.data.repositories

import com.daily.app.common.Resource
import com.daily.app.common.mapper.toArticle
import com.daily.app.data.local.entities.ArticleEnt
import com.daily.app.data.remote.AppAPI
import com.daily.app.data.remote.dto.ResponseDto
import com.daily.app.domain.model.Article
import com.daily.app.domain.repositories.AppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImp @Inject constructor(
    private val appAPI: AppAPI
) : AppRepo{
    override fun getTopHeadlines(
        country: String,
        lang: String,
        limit: String
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteNews = try {
                appAPI.getTopHeadlines(country, lang, limit)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteNews?.let { articleDtos ->

                val articles = mutableListOf<Article>()
                articleDtos.articles.map {
                    articles.add(it.toArticle())
                }

                emit(Resource.Success(articles))
                emit(Resource.Loading(false))
            }
        }
    }
}