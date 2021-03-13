package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(

	@field:SerializedName("data")
	val data: List<StatusItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class StatusItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
