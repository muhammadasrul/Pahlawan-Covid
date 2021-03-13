package com.asrul.covidvaccine.ui.riwayat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.ServiceRegistItem
import com.asrul.covidvaccine.data.api.response.ServiceRegistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatViewModel: ViewModel() {

    private val _riwayat = MutableLiveData<List<ServiceRegistItem?>>()
    val riwayat: LiveData<List<ServiceRegistItem?>> =_riwayat

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataNotEmpty = MutableLiveData<Boolean>()
    val isDataNotEmpty: LiveData<Boolean> = _isDataNotEmpty

    fun getRiwayat(user_id: String?) {
        _isLoading.value = true
        ApiConfig.getApiUser().getServiceRegist(user_id)
            .enqueue(object: Callback<ServiceRegistResponse> {
                override fun onResponse(
                    call: Call<ServiceRegistResponse>,
                    response: Response<ServiceRegistResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body()!!.data != null) {
                        val data = response.body()!!.data
                        _riwayat.value = data

                        Log.e("onReponse", "isNotEmpty")
                        _isDataNotEmpty.value = true
                        if (response.body()!!.data!!.isEmpty()) {
                            _isDataNotEmpty.value = false
                            Log.e("onReponse", "isEmpty")
                        }
                    } else {
                        Log.e("RiwayatViewModel", "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ServiceRegistResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}