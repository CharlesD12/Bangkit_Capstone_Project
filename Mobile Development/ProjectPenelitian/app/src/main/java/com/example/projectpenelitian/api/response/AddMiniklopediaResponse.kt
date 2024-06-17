package com.example.projectpenelitian.api.response

import com.google.gson.annotations.SerializedName

data class AddMiniklopediaResponse(

	@field:SerializedName("data")
	val data: MiniklopediaData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Timestamp(
	val any: Any? = null
)

data class MiniklopediaData(

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
	val timestamp: Timestamp? = null
)
