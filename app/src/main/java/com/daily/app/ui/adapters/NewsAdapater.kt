package com.daily.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.daily.app.R
import com.daily.app.common.utils.AppUtils
import com.daily.app.domain.model.Article

class NewsAdapater constructor(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<NewsAdapater.NewsHolder>() {

    private var articles: List<Article> = listOf()

    class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.news_source_favicon)
        lateinit var sourceFavicon: ImageView

        @BindView(R.id.news_source_site)
        lateinit var sourceSite: TextView


        @BindView(R.id.news_thumbnail)
        lateinit var newsThumbnail: ImageView

        @BindView(R.id.news_title)
        lateinit var newsTitle: TextView

        @BindView(R.id.news_published_date)
        lateinit var newsPublishedDate: TextView

        init {
            ButterKnife.bind(this, view)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val article: Article = articles[position]

        article.source.favicon?.let {
            requestManager.load(it).into(holder.sourceFavicon)
        }

        holder.sourceSite.text = AppUtils.urlGrooming(article.source.url)

        article.thumbnail?.let {
            requestManager.load(it).into(holder.newsThumbnail)
        }

        holder.newsTitle.text = article.title
        holder.newsPublishedDate.text = article.published_date
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateNewsList(articles: List<Article>) {
        this.articles = articles
    }
}