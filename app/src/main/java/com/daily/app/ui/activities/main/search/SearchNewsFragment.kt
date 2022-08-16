package com.daily.app.ui.activities.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.daily.app.R
import com.daily.app.common.AppPreferences
import com.daily.app.common.Resource
import com.daily.app.domain.model.AppConfig
import com.daily.app.ui.adapters.NewsAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private val TAG = "SearchNewsFragment"
    private val viewModel: SearchNewsViewModel by viewModels()

    @Inject
    lateinit var requestManager: RequestManager

    @BindView(R.id.newslist)
    lateinit var newsList: RecyclerView

    @BindView(R.id.txt_search)
    lateinit var txtSearch: EditText

    @BindView(R.id.layout_loading)
    lateinit var loadingLayout: ConstraintLayout

    @BindView(R.id.layout_error)
    lateinit var loadingError: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        val newsAdapter = NewsAdapter(requireContext(), requestManager)
        val prefJson = AppPreferences.getDefaultPreference(requireActivity().applicationContext)
        val appConfig = Gson().fromJson(prefJson, AppConfig::class.java)
        var pageCount = 1
        var term = ""

        viewModel.getArticles().observe(viewLifecycleOwner) {

            loadingError.visibility = View.GONE

            when(it) {
                is Resource.Success -> {
                    loadingError.visibility = View.GONE
                    newsList.visibility = View.VISIBLE

                    Log.d(TAG, "onViewCreated: $it")
                    it.data?.let { articles ->
                        Log.d(TAG, "onViewCreated: updateNewsList")
                        newsAdapter.updateNewsList(articles)
                        newsAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    newsList.visibility = View.GONE
                    loadingError.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    loadingLayout.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                pageCount = 1 // Reset page count
                term = editable.toString()
                viewModel.searchNews(term, appConfig.country, appConfig.language, appConfig.filter_limit)
            }

        })

        newsList.layoutManager = LinearLayoutManager(activity)
        newsList.adapter = newsAdapter
        newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (loadingLayout.visibility != View.VISIBLE) {
                        val limit = (appConfig.filter_limit.toInt() * (++pageCount))
                        viewModel.searchNews(term, appConfig.country, appConfig.language, limit.toString())
                    }
                }
            }
        })
    }
}