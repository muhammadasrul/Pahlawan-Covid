package com.asrul.covidvaccine.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceRegistResponse(

	@field:SerializedName("data")
	val data: List<ServiceRegistItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ServiceRegistItem(

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("confirm_status")
	val confirmStatus: String? = null,

	@field:SerializedName("regist_date")
	val registDate: String? = null,

	@field:SerializedName("hospital_id")
	val hospitalId: String? = null,

	@field:SerializedName("regist_id")
	val registId: String? = null,

	@field:SerializedName("total_payment")
	val totalPayment: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("service")
	val service: String? = null,

	@field:SerializedName("service_date")
	val serviceDate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("payment_method")
	val paymentMethod: String? = null,

	@field:SerializedName("qr_code")
	val qrCode: String? = null
): Parcelable
