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

        viewModel.getArticles().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
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
                }
            }
        }

        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val term = editable.toString()
                viewModel.searchNews(
                    term,
                    appConfig.country,
                    appConfig.language,
                    appConfig.filter_limit
                )
            }

        })

        newsList.layoutManager = LinearLayoutManager(activity)
        newsList.adapter = newsAdapter
    }
}