package com.daily.app.ui.activities.main.top

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {

    private val TAG = "TopNewsViewModel"

    private val articles = MutableLiveData<Resource<List<Article>>>()
    var job: Job? = null

    fun getTopHeadlines(
        country: String = "US",
        lang: String = "en",
        limit: String = "50"
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            appRepo.getTopHeadlines(country, lang, limit)
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