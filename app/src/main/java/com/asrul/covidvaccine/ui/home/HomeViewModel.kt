package com.asrul.covidvaccine.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _confirmed = MutableLiveData<Confirmed>()
    val confirmed: LiveData<Confirmed> = _confirmed

    private val _recovered = MutableLiveData<Recovered>()
    val recovered: LiveData<Recovered> = _recovered

    private val _deaths = MutableLiveData<Deaths>()
    val deaths: LiveData<Deaths> = _deaths

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCovidIndonesia() {
        _isLoading.value = true
        ApiConfig.getCovidIndonesia().getCovidIndonesia()
                .enqueue(object: Callback<CovidIndonesiaResponse> {
                    override fun onResponse(
                        call: Call<CovidIndonesiaResponse>,
                        response: Response<CovidIndonesiaResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val data = response.body()!!

                                _confirmed.value = data.confirmed
                                _recovered.value = data.recovered
                                _deaths.value = data.deaths
                            }
                        } else {
                            Log.e("HomeViewModel", "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<CovidIndonesiaResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }
}