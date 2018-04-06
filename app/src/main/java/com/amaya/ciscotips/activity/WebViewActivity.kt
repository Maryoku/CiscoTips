package com.amaya.ciscotips.activity

import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import android.webkit.WebView
import com.amaya.ciscotips.R
import android.os.Bundle
import android.webkit.WebSettings
import com.amaya.ciscotips.App

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val link = intent.extras.getString("link")
        val title = intent.extras.getString("title")
        setTitle(title)

        val webView = findViewById<WebView>(R.id.webView)

        if ((application as App).isOnline()) {

            webView.settings.loadsImagesAutomatically = true
            webView.settings.javaScriptEnabled = true
            WebView.SCROLLBARS_OUTSIDE_OVERLAY
            webView.loadDataWithBaseURL(null, "Loading", "text/html", "UTF-8", null)
            webView.settings.builtInZoomControls = true
            webView.settings.setSupportZoom(true)
            webView.settings.displayZoomControls = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
//        webView.settings.minimumFontSize = 40

            webView.webViewClient = WebViewClient()

            WebSettings.LOAD_DEFAULT

            webView.loadUrl(link)
        }
        else {
            webView.loadDataWithBaseURL(null, "Network ERROR. Please, check internet connection", "text/html", "UTF-8", null)
        }

    }
}
