package com.asrul.covidvaccine.ui.vaccine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.VaksinItem
import com.asrul.covidvaccine.data.api.response.VaksinStatsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaccineViewmodel: ViewModel() {

    private val _vaccineList = MutableLiveData<List<VaksinItem?>>()
    val vaccineList: LiveData<List<VaksinItem?>> = _vaccineList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getVaccineStats() {
        _isLoading.value = true
        ApiConfig.getApiUser().getVaksinStats()
            .enqueue(object : Callback<VaksinStatsResponse> {
                override fun onResponse(
                    call: Call<VaksinStatsResponse>,
                    response: Response<VaksinStatsResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body()!!.data != null) {
                        val data = response.body()!!.data

                        Log.e("vaksinprogress", response.body()!!.data?.get(0)?.pembaruanTerakhir.toString())
                        _vaccineList.value = data
                    }
                }

                override fun onFailure(call: Call<VaksinStatsResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}