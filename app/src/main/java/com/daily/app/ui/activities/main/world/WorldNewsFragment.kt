package com.daily.app.ui.activities.main.world

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.daily.app.R
import com.daily.app.common.Resource
import com.daily.app.ui.adapters.NewsAdaptor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorldNewsFragment : Fragment() {

    private val TAG = "WorldNewsFragment"
    private val viewModel: WorldNewsViewModel by viewModels()

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
        return inflater.inflate(R.layout.fragment_world_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        val newsAdapter = NewsAdaptor(requireContext(), requestManager)

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

        newsList.layoutManager = LinearLayoutManager(activity)
        newsList.adapter = newsAdapter
    }
}