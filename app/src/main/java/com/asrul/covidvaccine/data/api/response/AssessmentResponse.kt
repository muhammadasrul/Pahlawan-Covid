package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class AssessmentResponse(

		@field:SerializedName("data")
		val data: List<AssessmentItem?>? = null,

		@field:SerializedName("error")
		val error: Boolean? = null,

		@field:SerializedName("status")
		val status: Int? = null
)

data class AssessmentItem(

		@field:SerializedName("no")
		val no: String? = null,

		@field:SerializedName("score")
		val score: String? = null,

		@field:SerializedName("question")
		val question: String? = null,

		val answer: String? = null,
		var isAnswered: Boolean?,
		var checkedId: Int = -1
)
