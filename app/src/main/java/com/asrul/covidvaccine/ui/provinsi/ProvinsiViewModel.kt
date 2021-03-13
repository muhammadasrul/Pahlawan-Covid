package com.asrul.covidvaccine.ui.provinsi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.DataItem
import com.asrul.covidvaccine.data.api.response.ProvinsiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProvinsiViewModel: ViewModel() {

    val provinsi = MutableLiveData<List<DataItem?>>()
//    val provinsi: LiveData<List<DataItem>> = _provinsi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProvinsi() {
        _isLoading.value = true

        ApiConfig.getCovidProvinsi().getCovidProvinsi()
                .enqueue(object : Callback<ProvinsiResponse> {
                    override fun onResponse(call: Call<ProvinsiResponse>, response: Response<ProvinsiResponse>) {
                        _isLoading.value = false

                        if (response.isSuccessful && response.body() != null) {
                                val data = response.body()!!.data
//                                val provinsi = mutableListOf<DataItem>()
                                
//                                provinsi.addAll(data.data)

                                provinsi.value = data
                            } else {
                                Log.e("Provinsi", "onResponse: ${response.message()}")
                            }
                        }

                    override fun onFailure(call: Call<ProvinsiResponse>, t: Throwable) {
                        t.printStackTrace()
                    }


                })
    }
}