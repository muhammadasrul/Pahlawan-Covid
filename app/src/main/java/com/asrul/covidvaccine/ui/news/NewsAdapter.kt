package com.asrul.covidvaccine.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.response.ArticlesItem
import com.asrul.covidvaccine.databinding.NewsItemBinding
import com.bumptech.glide.Glide

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    val newsList = ArrayList<ArticlesItem?>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setNews(news: List<ArticlesItem?>) {
        this.newsList.clear()
        this.newsList.addAll(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem?) {
            with(binding) {
                txtTitle.text = news?.title
                txtDate.text = news?.publishedAt
                txtArticle.text = news?.content

                Glide.with(itemView.context)
                    .load(news?.urlToImage)
                    .placeholder(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(imgNews)

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(news)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: ArticlesItem?)
    }
}