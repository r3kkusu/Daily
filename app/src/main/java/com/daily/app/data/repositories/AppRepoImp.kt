package com.daily.app.data.repositories

import com.daily.app.common.Resource
import com.daily.app.common.mapper.toArticle
import com.daily.app.common.mapper.toArticleEnt
import com.daily.app.data.local.AppDB
import com.daily.app.data.local.AppDao
import com.daily.app.data.local.entities.enums.Categories
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
    private val appAPI: AppAPI,
    private val appDB: AppDB
) : AppRepo{

    private val dao: AppDao = appDB.dao

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
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            }

            remoteNews?.let { articleDtos ->
                try {
                    dao.clear(Categories.TOP.name)
                    articleDtos.articles.map {
                        dao.insert(it.toArticleEnt(Categories.TOP))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't save data to local cache"))
                    null
                }
            }

            val localNews = try {
                dao.getArticles(Categories.TOP.name)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from cache"))
                null
            }

            localNews?.let { articleEnt ->

                val articles = mutableListOf<Article>()
                articleEnt.map {
                    articles.add(it.toArticle())
                }

                emit(Resource.Success(articles))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun getTopicHeadlines(
        topic: String,
        country: String,
        lang: String,
        limit: String
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteNews = try {
                appAPI.getTopicHeadlines(topic, country, lang, limit)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            }

            remoteNews?.let { articleDtos ->
                try {
                    dao.clear(Categories.TOPIC.name)
                    articleDtos.articles.map {
                        dao.insert(it.toArticleEnt(Categories.TOPIC))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't save data to local cache"))
                    null
                }
            }

            val localNews = try {
                dao.getArticles(Categories.TOPIC.name)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from cache"))
                null
            }

            localNews?.let { articleEnt ->

                val articles = mutableListOf<Article>()
                articleEnt.map {
                    articles.add(it.toArticle())
                }

                emit(Resource.Success(articles))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun getLocalNews(
        geo: String,
        country: String,
        lang: String,
        limit: String
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteNews = try {
                appAPI.getGeolocation(geo, country, lang, limit)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from remote server"))
                null
            }

            remoteNews?.let { articleDtos ->
                try {
                    dao.clear(Categories.LOCAL.name)
                    articleDtos.articles.map {
                        dao.insert(it.toArticleEnt(Categories.LOCAL))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't save data to local cache"))
                    null
                }
            }

            val localNews = try {
                dao.getArticles(Categories.LOCAL.name)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't fetch data from cache"))
                null
            }

            localNews?.let { articleEnt ->

                val articles = mutableListOf<Article>()
                articleEnt.map {
                    articles.add(it.toArticle())
                }

                emit(Resource.Success(articles))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun searchNews(
        term: String,
        country: String,
        lang: String,
        limit: String
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteNews = try {
                appAPI.searchArticles(term, country, lang, limit)
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
            }

            emit(Resource.Loading(false))
        }
    }
}