package com.asrul.covidvaccine.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.ArticlesItem
import com.asrul.covidvaccine.data.api.response.BeritaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel: ViewModel() {

    private var _newsList = MutableLiveData<List<ArticlesItem?>>()
    var newsList: LiveData<List<ArticlesItem?>> = _newsList

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getNews() {
        _isLoading.value = true
        ApiConfig.getApiBerita().getBerita()
            .enqueue(object : Callback<BeritaResponse> {
                override fun onResponse(
                        call: Call<BeritaResponse>,
                        response: Response<BeritaResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val data = response.body()!!

                            _newsList.value = data.articles

                        } else {
                            Log.e("Berita", "isResponse: ${response.message()}")
                        }
                    }
                }

                override fun onFailure(call: Call<BeritaResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}