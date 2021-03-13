package com.asrul.covidvaccine.data.api

import com.asrul.covidvaccine.BuildConfig
import com.asrul.covidvaccine.data.api.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("user/register")
    fun getRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @Multipart
    @POST("user/{user_id}")
    fun postUser(
        @Path("user_id") user_id: String,
        @Part file_path: MultipartBody.Part,
        @PartMap profile: Map<String,@JvmSuppressWildcards RequestBody>
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/password")
    fun getPassword(
        @Field("id") id: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @GET("provinsi")
    fun getCovidProvinsi(): Call<ProvinsiResponse>

    @GET("countries/indonesia")
    fun getCovidIndonesia(): Call<CovidIndonesiaResponse>

    @GET("everything?q=corona&language=id&apiKey=${BuildConfig.NEWS_API_KEY}")
    fun getBerita(): Call<BeritaResponse>

    @GET("assessment")
    fun getAssessment(): Call<AssessmentResponse>

    @FormUrlEncoded
    @POST("assessment/history")
    fun postHistory(
        @Field("date") date: String,
        @Field("user_id") user_id: String,
        @Field("status") status: String,
    ): Call<HistoryResponse>

    @GET("service/vaksinasi")
    fun getVaccine(): Call<ServiceResponse>

    @GET("service/vaccine_province")
    fun getVaccineByProvince(
        @Query("province") province: String
    ): Call<ServiceResponse>

    @GET("service/vaccine_city")
    fun getVaccineByCity(
        @Query("city") city: String
    ): Call<ServiceResponse>

    @GET("service/rapid")
    fun getRapid(): Call<ServiceResponse>

    @GET("service/rapid_province")
    fun getRapidByProvince(
        @Query("province") province: String
    ): Call<ServiceResponse>

    @GET("service/rapid_city")
    fun getRapidByCity(
        @Query("city") city: String
    ): Call<ServiceResponse>

    @GET("provinsi")
    fun getWilayahProvinsi(): Call<WilayahProvResponse>

    @GET("kota")
    fun getWilayahKota(
        @Query("id_provinsi") id_provinsi: String
    ): Call<WilayahKotaResponse>

    @GET("assessment/history")
    fun getStatus(
        @Query("user_id") user_id: String?
    ): Call<StatusResponse>

    @GET("service/payment")
    fun getPayment(): Call<PaymentResponse>

    @FormUrlEncoded
    @POST("service/service_regist")
    fun postServiceRegist(
        @Field("regist_id") regist_id: String?,
        @Field("user_id") user_id: String?,
        @Field("hospital_id") hospital_id: String?,
        @Field("service") service: String?,
        @Field("total_payment") total_payment: String?,
        @Field("payment_method") payment_method: String?,
        @Field("full_name") full_name: String,
        @Field("nik") nik: String,
        @Field("regist_date") regist_date: String,
        @Field("service_date") service_date: String,
        @Field("birthday") birthday: String,
        @Field("gender") gender: String
    ): Call<List<ServiceRegistResponse>>

    @GET("service/service_regist/{user_id}")
    fun getServiceRegist(
        @Path("user_id") user_id: String?
    ): Call<ServiceRegistResponse>

    @GET("vaksinasi")
    fun getVaksinStats(): Call<VaksinStatsResponse>
}