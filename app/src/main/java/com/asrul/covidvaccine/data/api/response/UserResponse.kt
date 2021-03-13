package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("data")
    val data: UserData? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class UserData(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("photo_path")
    val photo_path: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("birthday")
    val birthday: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("role")
    val role: Int? = null
)
