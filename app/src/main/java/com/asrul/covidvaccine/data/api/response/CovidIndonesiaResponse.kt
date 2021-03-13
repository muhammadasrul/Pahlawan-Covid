package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class CovidIndonesiaResponse(

	@field:SerializedName("recovered")
	val recovered: Recovered? = null,

	@field:SerializedName("lastUpdate")
	val lastUpdate: String? = null,

	@field:SerializedName("confirmed")
	val confirmed: Confirmed? = null,

	@field:SerializedName("deaths")
	val deaths: Deaths? = null
)

data class Recovered(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)

data class Deaths(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)

data class Confirmed(

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)
