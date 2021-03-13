package com.asrul.covidvaccine.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceResponse(

	@field:SerializedName("data")
	val data: List<ServiceItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class ServiceItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("service")
	val service: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("service_id")
	val serviceId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("hospital_id")
	val hospitalId: String? = null
) : Parcelable
