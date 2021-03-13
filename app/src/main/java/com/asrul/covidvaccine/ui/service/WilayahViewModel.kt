package com.asrul.covidvaccine.ui.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.KotaKabupatenItem
import com.asrul.covidvaccine.data.api.response.ProvinsiItem
import com.asrul.covidvaccine.data.api.response.WilayahKotaResponse
import com.asrul.covidvaccine.data.api.response.WilayahProvResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WilayahViewModel: ViewModel() {

    private val _provinsiList = MutableLiveData<List<ProvinsiItem?>>()
    val provinsiList: LiveData<List<ProvinsiItem?>> = _provinsiList

    private val _kotaList = MutableLiveData<List<KotaKabupatenItem?>>()
    val kotaList: LiveData<List<KotaKabupatenItem?>> = _kotaList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProvinsiList() {
        _isLoading.value = true
        ApiConfig.getWilayah().getWilayahProvinsi()
                .enqueue(object: Callback<WilayahProvResponse> {
                    override fun onResponse(call: Call<WilayahProvResponse>, response: Response<WilayahProvResponse>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!.provinsi
                            _provinsiList.value = data
                        } else {
                            Log.e("getProvinsiList", "onResponse: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<WilayahProvResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }

    fun getKotaList(id_provinsi: String) {

        ApiConfig.getWilayah().getWilayahKota(id_provinsi)
                .enqueue(object: Callback<WilayahKotaResponse> {
                    override fun onResponse(call: Call<WilayahKotaResponse>, response: Response<WilayahKotaResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!.kotaKabupaten
                            _kotaList.value = data
                        } else {
                            Log.e("getKotaList", "onResponse: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<WilayahKotaResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }

}