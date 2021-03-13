package com.asrul.covidvaccine.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        fun getCovidIndonesia(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://covid19.mathdro.id/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getCovidProvinsi(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://indonesia-covid-19.mathdro.id/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiBerita(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiUser(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://pahlawancovid.000webhostapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getWilayah(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://dev.farizdotid.com/api/daerahindonesia/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}