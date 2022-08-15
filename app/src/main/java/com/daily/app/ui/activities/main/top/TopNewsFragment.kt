package com.daily.app.ui.activities.main.top

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.daily.app.R
import com.daily.app.common.Resource
import com.daily.app.ui.adapters.NewsAdapater
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        val newsAdapter = NewsAdapater(requestManager)

        viewModel.getArticles().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: $it")
                    it.data?.let { articles ->
                        Log.d(TAG, "onViewCreated: updateNewsList")
                        newsAdapter.updateNewsList(articles)
                        newsAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        }

        newsList.layoutManager = LinearLayoutManager(activity)
        newsList.adapter = newsAdapter

    }
}