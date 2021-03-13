package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class VaksinStatsResponse(

	@field:SerializedName("data")
	val data: List<VaksinItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class VaksinItem(

	@field:SerializedName("petugas_publik")
	val petugasPublik: String? = null,

	@field:SerializedName("pembaruan_terakhir")
	val pembaruanTerakhir: String? = null,

	@field:SerializedName("vaksin_id")
	val vaksinId: String? = null,

	@field:SerializedName("semua")
	val semua: String? = null,

	@field:SerializedName("t_lansia")
	val tLansia: String? = null,

	@field:SerializedName("lansia")
	val lansia: String? = null,

	@field:SerializedName("t_tenaga_kesehatan")
	val tTenagaKesehatan: String? = null,

	@field:SerializedName("tenaga_kesehatan")
	val tenagaKesehatan: String? = null,

	@field:SerializedName("tahap")
	val tahap: String? = null,

	@field:SerializedName("t_petugas_publik")
	val tPetugasPublik: String? = null,

	@field:SerializedName("t_semua")
	val tSemua: String? = null
)
