package com.codedirect.laundry.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseJSON(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("users")
	val users: List<UsersItems?>? = null,

	@field:SerializedName("transactions")
	val transactions: List<TransactionsItems?>? = null
)