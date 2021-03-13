package com.asrul.covidvaccine.ui.assessment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.AssessmentItem
import com.asrul.covidvaccine.data.api.response.AssessmentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentViewModel {
    val assessment = MutableLiveData<List<AssessmentItem?>>()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAssessment() {
        _isLoading.value = true

        ApiConfig.getApiUser().getAssessment()
            .enqueue(object: Callback<AssessmentResponse> {
                override fun onResponse(
                        call: Call<AssessmentResponse>,
                        response: Response<AssessmentResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val data = response.body()!!

                            assessment.value = data.data
                        }
                    } else {
                        Log.e("Assessment", "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AssessmentResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}