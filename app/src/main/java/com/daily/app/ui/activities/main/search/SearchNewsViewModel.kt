package com.daily.app.ui.activities.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daily.app.common.Resource
import com.daily.app.domain.model.Article
import com.daily.app.domain.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {

    private val TAG = "SearchNewsViewModel"

    private val articles = MutableLiveData<Resource<List<Article>>>()
    var job: Job? = null

    fun searchNews(
        term: String = "Covid",
        country: String = "US",
        lang: String = "en",
        limit: String = "50",
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            delay(500) // Delay haft a second to avoid extensive queries
            appRepo.searchNews(term, country, lang, limit)
                .collect { result ->
                    withContext(Dispatchers.Main) { articles.postValue(result)
                    }
                }
        }
    }

    fun getArticles() : LiveData<Resource<List<Article>>> {
        return articles
    }
}