package com.asrul.covidvaccine.ui.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asrul.covidvaccine.data.api.response.ArticlesItem
import com.asrul.covidvaccine.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var fragmentNewsBinding: FragmentNewsBinding? = null
    private val binding get() = fragmentNewsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = NewsViewModel()

        viewModel.getNews()
        viewModel.newsList.observe(viewLifecycleOwner, { news->
            val newsAdapter = NewsAdapter()
            newsAdapter.setNews(news)
            newsAdapter.notifyDataSetChanged()

            binding.rvBerita.layoutManager = LinearLayoutManager(context)
            binding.rvBerita.setHasFixedSize(true)
            binding.rvBerita.adapter = newsAdapter

            newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
                override fun onItemClicked(news: ArticlesItem?) {
                    val newsUrl = news?.url
                    val intent = Intent(context, WebViewActivity::class.java)
                    intent.putExtra(WebViewActivity.EXTRA_NEWS, newsUrl)
                    startActivity(intent)
                }

            })
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}