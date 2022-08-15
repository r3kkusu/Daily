package com.daily.app.ui.activities.news

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.daily.app.R
import com.daily.app.common.Constants

class NewsActivity : AppCompatActivity() {

    @BindView(R.id.btn_back)
    lateinit var backButton: ImageButton

    @BindView(R.id.web_view)
    lateinit var webViewer: WebView

    @BindView(R.id.layout_loading)
    lateinit var loadingLayout: ConstraintLayout

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        ButterKnife.bind(this)

        backButton.setOnClickListener {
            onBackPressed()
        }

        webViewer.settings.javaScriptEnabled = true
        webViewer.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, facIcon: Bitmap?) {
                loadingLayout.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                loadingLayout.visibility = View.GONE
            }
        }

        intent.getStringExtra(Constants.INTENT_URL)?.let {
            webViewer.loadUrl(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}