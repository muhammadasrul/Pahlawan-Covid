package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

		@field:SerializedName("data")
		val data: HistoryData? = null,

		@field:SerializedName("error")
		val error: Boolean? = null,

		@field:SerializedName("message")
		val message: String? = null,

		@field:SerializedName("status")
		val status: Int? = null
)

data class HistoryData(

		@field:SerializedName("date")
		val date: String? = null,

		@field:SerializedName("user_id")
		val userId: String? = null,

		@field:SerializedName("status")
		val status: String? = null
)
