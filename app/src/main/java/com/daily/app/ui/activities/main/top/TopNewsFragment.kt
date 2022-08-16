package com.daily.app.ui.activities.main.top

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
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
class TopNewsFragment : Fragment() {

    private val TAG = "TopNewsFragment"
    private val viewModel: TopNewsViewModel by viewModels()

    @Inject
    lateinit var requestManager: RequestManager

    @BindView(R.id.newslist)
    lateinit var newsList: RecyclerView

    @BindView(R.id.layout_loading)
    lateinit var loadingLayout: ConstraintLayout

    @BindView(R.id.layout_error)
    lateinit var loadingError: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        val newsAdapter = NewsAdapter(requireContext(), requestManager)
        val prefJson = AppPreferences.getDefaultPreference(requireActivity().applicationContext)
        val appConfig = Gson().fromJson(prefJson, AppConfig::class.java)
        var pageCount = 1

        viewModel.getArticles().observe(viewLifecycleOwner) {
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
                }
                is Resource.Loading -> {
                    loadingLayout.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
        viewModel.getTopHeadlines(appConfig.country, appConfig.language, appConfig.filter_limit)

        newsList.layoutManager = LinearLayoutManager(activity)
        newsList.adapter = newsAdapter
        newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (loadingLayout.visibility != View.VISIBLE) {
                        val limit = (appConfig.filter_limit.toInt() * (++pageCount))
                        viewModel.getTopHeadlines(appConfig.country, appConfig.language, limit.toString())
                    }
                }
            }
        })
    }
}