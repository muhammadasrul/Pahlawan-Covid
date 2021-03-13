package com.asrul.covidvaccine.data.api.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

	@field:SerializedName("data")
	val data: List<PaymentItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class PaymentItem(

	@field:SerializedName("id_payment")
	val idPayment: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("payment_logo")
	val paymentLogo: String? = null
)
