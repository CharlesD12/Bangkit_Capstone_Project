package com.example.projectpenelitian.api.response

import com.google.gson.annotations.SerializedName

data class AddBookmarkResponse(

	@field:SerializedName("data")
	val data: bookmarkData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class bookmarkTimestamp(
	val any: Any? = null
)

data class bookmarkData(

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String? = null,

	@field:SerializedName("jenis_penyakit")
	val jenisPenyakit: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: bookmarkTimestamp? = null
)
