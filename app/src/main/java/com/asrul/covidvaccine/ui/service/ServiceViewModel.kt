package com.asrul.covidvaccine.ui.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.ServiceItem
import com.asrul.covidvaccine.data.api.response.ServiceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceViewModel: ViewModel() {

    private val _listService = MutableLiveData<List<ServiceItem?>>()
    val listService: LiveData<List<ServiceItem?>> = _listService

    private  val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _isDataNotEmpty = MutableLiveData<Boolean>()
    val isDataNotEmpty: LiveData<Boolean> = _isDataNotEmpty

    fun getVaccine() {
        _isLoading.value = true
        ApiConfig.getApiUser().getVaccine()
                .enqueue(object: Callback<ServiceResponse?> {
                    override fun onResponse(call: Call<ServiceResponse?>, response: Response<ServiceResponse?>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data


                            Log.e("onReponse", "isNotEmpty")
                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse?>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }

    fun getVaccineByProvince(province: String) {
        _isLoading.value = true
        ApiConfig.getApiUser().getVaccineByProvince(province)
                .enqueue(object: Callback<ServiceResponse?> {
                    override fun onResponse(call: Call<ServiceResponse?>, response: Response<ServiceResponse?>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data

                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse?>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }

    fun getVaccineByCity(city: String) {
        _isLoading.value = true
        ApiConfig.getApiUser().getVaccineByCity(city)
                .enqueue(object: Callback<ServiceResponse?> {
                    override fun onResponse(call: Call<ServiceResponse?>, response: Response<ServiceResponse?>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data

                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse?>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }

    fun getRapid() {
        _isLoading.value = true
        ApiConfig.getApiUser().getRapid()
                .enqueue(object: Callback<ServiceResponse> {
                    override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data

                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }

    fun getRapidByProvince(province: String) {
        _isLoading.value = true
        ApiConfig.getApiUser().getRapidByProvince(province)
                .enqueue(object: Callback<ServiceResponse> {
                    override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data

                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }

    fun getRapidByCity(city: String) {
        _isLoading.value = true
        ApiConfig.getApiUser().getRapidByCity(city)
                .enqueue(object: Callback<ServiceResponse> {
                    override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                        _isLoading.value = false
                        if (response.isSuccessful && response.body() != null ) {
                            val data = response.body()!!.data
                            _listService.value = data

                            _isDataNotEmpty.value = true
                            if (response.body()!!.data!!.isEmpty()) {
                                _isDataNotEmpty.value = false
                                Log.e("onReponse", "isEmpty")
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
    }
}