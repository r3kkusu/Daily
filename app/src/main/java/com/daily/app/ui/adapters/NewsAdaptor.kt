package com.daily.app.ui.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.daily.app.R
import com.daily.app.common.Constants
import com.daily.app.common.utils.AppUtils
import com.daily.app.common.utils.DateUtils
import com.daily.app.domain.model.Article
import com.daily.app.ui.activities.news.NewsActivity

class NewsAdaptor constructor(
    private val context: Context,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<NewsAdaptor.NewsHolder>() {

    private var articles: List<Article> = listOf()

    class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.layout_root)
        lateinit var layoutRoot: LinearLayout

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val article: Article = articles[position]

        holder.layoutRoot.setOnClickListener {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, article.link)
            context.startActivity(intent)
        }

        article.source.favicon?.let {
            requestManager.load(it).into(holder.sourceFavicon)
        }

        holder.sourceSite.text = AppUtils.urlGrooming(article.source.url)

        article.thumbnail?.let {
            requestManager.load(it).into(holder.newsThumbnail)
        }

        holder.newsTitle.text = article.title

        val date1 = DateUtils.format(article.published_date, DateUtils.DATE_FORMAT_3);
        val date2 = DateUtils.now()
        val timeDiff = DateUtils.dateDiff(date1, date2)
        val timeDiffDesc = DateUtils.dateDiffDescription(timeDiff)

        holder.newsPublishedDate.text = timeDiffDesc
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateNewsList(articles: List<Article>) {
        this.articles = articles
    }
}