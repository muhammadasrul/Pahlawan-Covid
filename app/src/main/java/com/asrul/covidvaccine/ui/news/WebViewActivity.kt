package com.asrul.covidvaccine.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asrul.covidvaccine.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_NEWS).toString()
        binding.webView.loadUrl(url)
    }
}