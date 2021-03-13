package com.asrul.covidvaccine.ui.service.payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.PaymentItem
import com.asrul.covidvaccine.data.api.response.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel: ViewModel() {

    private val _paymentList = MutableLiveData<List<PaymentItem?>>()
    val paymentList: LiveData<List<PaymentItem?>> = _paymentList

    fun getPaymentList() {
        ApiConfig.getApiUser().getPayment()
                .enqueue(object: Callback<PaymentResponse?> {
                    override fun onResponse(call: Call<PaymentResponse?>, response: Response<PaymentResponse?>) {
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!.data
                            _paymentList.value = data
                        } else {
                            Log.e("PaymentViewModel", "onResponse: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<PaymentResponse?>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }
}