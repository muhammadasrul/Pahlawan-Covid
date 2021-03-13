package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class WilayahKotaResponse(

	@field:SerializedName("kota_kabupaten")
	val kotaKabupaten: List<KotaKabupatenItem?>? = null
)

data class KotaKabupatenItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_provinsi")
	val idProvinsi: String? = null
)
