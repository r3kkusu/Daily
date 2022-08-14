package com.daily.app.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daily.app.common.Resource
import com.daily.app.domain.model.Article
import com.daily.app.domain.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {

    private val TAG = "MainViewModel"
    private val defaultGeolocation = "New York"
    private val defaultCountry = "US"
    private val defaultLimit = "50"
    private val defaultLanguage = "en"

    private val articles = MutableLiveData<Resource<List<Article>>>()
    var job : Job? = null

    init {
        getTopicHeadlines()
//        getLocalNews()
//        searchNews()
    }



    fun getTopicHeadlines(
        topic: String = "WORLD",
        country: String = defaultCountry,
        lang: String = defaultLanguage,
        limit: String = defaultLimit
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            appRepo.getTopicHeadlines(topic, country, lang, limit)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        articles.postValue(result)
                    }
                }
        }
    }

    fun getLocalNews(
        geo: String = defaultGeolocation,
        country: String = defaultCountry,
        lang: String = defaultLanguage,
        limit: String = defaultLimit
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            appRepo.getLocalNews(geo, country, lang, limit)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        articles.postValue(result)
                    }
                }
        }
    }

    fun searchNews(
        term: String = "Covid",
        country: String = defaultCountry,
        lang: String = defaultLanguage,
        limit: String = defaultLimit,
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            appRepo.searchNews(term, country, lang, limit)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        articles.postValue(result)
                    }
                }
        }
    }

    fun getArticles() : LiveData<Resource<List<Article>>> {
        return articles
    }
}